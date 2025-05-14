package ddbb;
import classes.Battle;
import classes.GlobalContext;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class BattleXmlGenerator {

    private BattleXmlGenerator() {

    }

    // Uses Battle object to generate the xml file
    public static void generateXml(Battle battleObject) {
        try {
            // Creating document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Element battle with attribute id
            Element battle = doc.createElement("battle");
            battle.setAttribute("id", Integer.toString(GlobalContext.num_battle));
            doc.appendChild(battle);

            // Element army_planet
            Element armyPlanet = createArmyElement(doc, "army_planet", battleObject);
            battle.appendChild(armyPlanet);

            // Element army_enemy
            Element armyEnemy = createArmyElement(doc, "army_enemy", battleObject);
            battle.appendChild(armyEnemy);

            // Element waste_generated
            Element waste = doc.createElement("waste_generated");
            String metalWaste = Integer.toString(battleObject.getWasteMetalDeuterium()[0]);
            addSimpleElement(doc, waste, "metal", metalWaste);
            String deuteriumWaste = Integer.toString(battleObject.getWasteMetalDeuterium()[1]);
            addSimpleElement(doc, waste, "deuterium", deuteriumWaste);
            battle.appendChild(waste);

            // Element winner
            String winner = "planet";
            if (battleObject.getWinner() == 1) {
                winner = "enemy";
            }
            addSimpleElement(doc, battle, "winner", winner);

            // Save file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            String fileName = "./M4-Marcas/battles/xml/battle" + GlobalContext.num_battle + ".xml";
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

            System.out.println("XML file created in ./M4-Marcas/battles/xml/");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creates element army_planet or army_enemy
    private static Element createArmyElement(Document doc, String tagName, Battle battleObject) {

        // Element <type_of_army>
        Element army = doc.createElement(tagName);

        // Nested elements and index to look for depends of army type
        int armyIndex = 0;
        String[] units = {
            "light_hunter",
            "heavy_hunter",
            "battle_ship",
            "armored_ship"
        };
        if (tagName.equals("army_enemy")) {
            armyIndex = 1;
            units = new String[] {
                "light_hunter",
                "heavy_hunter",
                "battle_ship",
                "armored_ship",
                "missile_launcher",
                "ion_cannon",
                "plasma_cannon"
            };
        }

        // Elements unit
        int unitIndex = 0;
        for (String unit : units) {
            Element unitElement = doc.createElement(unit);
            String unitUnits = Integer.toString(battleObject.getInitialArmies()[armyIndex][unitIndex]);
            addSimpleElement(doc, unitElement, "units", unitUnits);
            String unitDrops = Integer.toString(battleObject.getInitialArmies()[armyIndex][unitIndex] - battleObject.getPlanetArmy()[unitIndex].size());
            addSimpleElement(doc, unitElement, "drops", "0");
            army.appendChild(unitElement);
            unitIndex++;
        }

        // Element total_cost
        Element totalCost = doc.createElement("total_cost");
        String metalCost = Integer.toString(battleObject.getInitialCostFleet()[armyIndex][0]);
        addSimpleElement(doc, totalCost, "metal", metalCost);
        String deuteriumCost = Integer.toString(battleObject.getInitialCostFleet()[armyIndex][1]);
        addSimpleElement(doc, totalCost, "deuterium", deuteriumCost);
        army.appendChild(totalCost);

        // Element losses
        Element losses = doc.createElement("losses");
        String metalLosses = Integer.toString(battleObject.getResourcesLosses()[armyIndex][0]);
        addSimpleElement(doc, losses, "metal", metalLosses);
        String deuteriumLosses = Integer.toString(battleObject.getResourcesLosses()[armyIndex][1]);
        addSimpleElement(doc, losses, "deuterium", deuteriumLosses);
        String weightedLosses = Integer.toString(battleObject.getResourcesLosses()[armyIndex][2]);
        addSimpleElement(doc, losses, "weighted", weightedLosses);
        army.appendChild(losses);

        return army;
    }

    // Creating element <tag>value</tag> inside upper element
    private static void addSimpleElement(Document doc, Element parent, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(value));
        parent.appendChild(element);
    }
}