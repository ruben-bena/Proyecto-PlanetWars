<?xml version="1.0" encoding="UTF-8"?>
<!-- XSLT stylesheet for transforming battle XML data into HTML battle reports -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!-- Output configuration -->
    <xsl:output method="html" indent="yes"/>
    
    <!-- Main template for battle report -->
    <xsl:template match="/battle">
        <!-- Battle report container -->
        <div class="reporte-batalla">
            <h2>BATTLE #<xsl:value-of select="@id"/> STATISTICS</h2>

            <!-- Battle type section -->
            <div class="type-battle">
                <xsl:attribute name="class">
                    <xsl:choose>
                        <xsl:when test="invasion = 'true'">type-battle invasion</xsl:when>
                        <xsl:otherwise>type-battle external-menace</xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
                <h3>Type of battle</h3>
                <p> 
                    <xsl:choose>
                        <xsl:when test="invasion = 'true'">Invasion ⚔️</xsl:when>
                        <xsl:otherwise>External menace 🛡️</xsl:otherwise>
                    </xsl:choose>
                </p>
            </div>

            <!-- Army comparison section -->
            <div class="comparison">
                <h3>Army Comparison</h3>
                <table class="army-comparison">
                    <thead>
                        <tr>
                            <th rowspan="2">Unit type</th>
                            <th colspan="2">Planet</th>
                            <th colspan="2">Enemy</th>
                        </tr>
                        <tr>
                            <th>Units</th>
                            <th>Drop</th>
                            <th>Units</th>
                            <th>Drop</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Generate unit rows using template -->
                        <xsl:call-template name="generateUnitRows"/>
                    </tbody>
                </table>
            </div>
                            
            <!-- Costs section -->
            <div class="costs">
                <h3>Costs</h3>
                <div class="costs-container">
                    <!-- Planet costs -->
                    <div class="cost-planet">
                        <h4>Planet</h4>
                        <ul>
                            <li>Metal: <xsl:value-of select="format-number(army_planet/total_cost/metal, '#,###')"/></li>
                            <li>Deuterium: <xsl:value-of select="format-number(army_planet/total_cost/deuterium, '#,###')"/></li>
                        </ul>
                    </div>
                    <!-- Enemy costs -->
                    <div class="cost-enemy">
                        <h4>Enemy</h4>
                        <ul>
                            <li>Metal: <xsl:value-of select="format-number(army_enemy/total_cost/metal, '#,###')"/></li>
                            <li>Deuterium: <xsl:value-of select="format-number(army_enemy/total_cost/deuterium, '#,###')"/></li>
                        </ul>
                    </div>
                </div>
            </div>
                            
            <!-- Losses section -->
            <div class="losses">
                <h3>Losses</h3>
                <div class="losses-container">
                    <!-- Planet losses -->
                    <div class="losses-planet">
                        <h4>Planet</h4>
                        <ul>
                            <li>Metal: <xsl:value-of select="format-number(army_planet/losses/metal, '#,###')"/></li>
                            <li>Deuterium: <xsl:value-of select="format-number(army_planet/losses/deuterium, '#,###')"/></li>
                            <li>Weighted: <xsl:value-of select="format-number(army_planet/losses/weighted, '#,###')"/></li>
                        </ul>
                    </div>
                    <!-- Enemy losses -->
                    <div class="losses-enemy">
                        <h4>Enemy</h4>
                        <ul>
                            <li>Metal: <xsl:value-of select="format-number(army_enemy/losses/metal, '#,###')"/></li>
                            <li>Deuterium: <xsl:value-of select="format-number(army_enemy/losses/deuterium, '#,###')"/></li>
                            <li>Weighted: <xsl:value-of select="format-number(army_enemy/losses/weighted, '#,###')"/></li>
                        </ul>
                    </div>
                </div>
            </div>
                            
            <!-- Waste generated section -->
            <div class="waste">
                <h3>Waste Generated</h3>
                <ul>
                    <li>Metal: <xsl:value-of select="format-number(waste_generated/metal, '#,###')"/></li>
                    <li>Deuterium: <xsl:value-of select="format-number(waste_generated/deuterium, '#,###')"/></li>
                </ul>
            </div>
            
            <!-- Result section -->
            <div class="result">
                <xsl:attribute name="class">
                    <xsl:choose>
                        <xsl:when test="winner = 'planet'">result victory</xsl:when>
                        <xsl:otherwise>result defeat</xsl:otherwise>
                    </xsl:choose>
                </xsl:attribute>
                <h3>Winner</h3>
                <p>
                    <xsl:choose>
                        <xsl:when test="winner = 'planet'">Planet 🌍</xsl:when>
                        <xsl:otherwise>Enemy 👽</xsl:otherwise>
                    </xsl:choose>
                </p>
            </div>
            
            <!-- Download button -->
            <button class="btn-download" onclick="downloadBattleXML()">Download battle XML</button>
        </div>
    </xsl:template>
    
    <!-- Template to generate unit rows for the army comparison table -->
    <xsl:template name="generateUnitRows">
        <tr>
            <td>Light Hunter</td>
            <td><xsl:value-of select="army_planet/light_hunter/units"/></td>
            <td><xsl:value-of select="army_planet/light_hunter/drops"/></td>
            <td><xsl:value-of select="army_enemy/light_hunter/units"/></td>
            <td><xsl:value-of select="army_enemy/light_hunter/drops"/></td>
        </tr>
        
        <tr>
            <td>Heavy Hunter</td>
            <td><xsl:value-of select="army_planet/heavy_hunter/units"/></td>
            <td><xsl:value-of select="army_planet/heavy_hunter/drops"/></td>
            <td><xsl:value-of select="army_enemy/heavy_hunter/units"/></td>
            <td><xsl:value-of select="army_enemy/heavy_hunter/drops"/></td>
        </tr>
        
        <tr>
            <td>Battle Ship</td>
            <td><xsl:value-of select="army_planet/battle_ship/units"/></td>
            <td><xsl:value-of select="army_planet/battle_ship/drops"/></td>
            <td><xsl:value-of select="army_enemy/battle_ship/units"/></td>
            <td><xsl:value-of select="army_enemy/battle_ship/drops"/></td>
        </tr>
        
        <tr>
            <td>Armored Ship</td>
            <td><xsl:value-of select="army_planet/armored_ship/units"/></td>
            <td><xsl:value-of select="army_planet/armored_ship/drops"/></td>
            <td><xsl:value-of select="army_enemy/armored_ship/units"/></td>
            <td><xsl:value-of select="army_enemy/armored_ship/drops"/></td>
        </tr>
        
        <tr>
            <td>Missile Launcher</td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'">-</xsl:when>
                    <xsl:otherwise><xsl:value-of select="army_planet/missile_launcher/units"/></xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'">-</xsl:when>
                    <xsl:otherwise><xsl:value-of select="army_planet/missile_launcher/drops"/></xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'"><xsl:value-of select="army_enemy/missile_launcher/units"/></xsl:when>
                    <xsl:otherwise>-</xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'"><xsl:value-of select="army_enemy/missile_launcher/drops"/></xsl:when>
                    <xsl:otherwise>-</xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>
        
        <tr>
            <td>Ion Cannon</td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'">-</xsl:when>
                    <xsl:otherwise><xsl:value-of select="army_planet/ion_cannon/units"/></xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'">-</xsl:when>
                    <xsl:otherwise><xsl:value-of select="army_planet/ion_cannon/drops"/></xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'"><xsl:value-of select="army_enemy/ion_cannon/units"/></xsl:when>
                    <xsl:otherwise>-</xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'"><xsl:value-of select="army_enemy/ion_cannon/drops"/></xsl:when>
                    <xsl:otherwise>-</xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>
        
        <tr>
            <td>Plasma Cannon</td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'">-</xsl:when>
                    <xsl:otherwise><xsl:value-of select="army_planet/plasma_cannon/units"/></xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'">-</xsl:when>
                    <xsl:otherwise><xsl:value-of select="army_planet/plasma_cannon/drops"/></xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'"><xsl:value-of select="army_enemy/plasma_cannon/units"/></xsl:when>
                    <xsl:otherwise>-</xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <xsl:choose>
                    <xsl:when test="invasion = 'true'"><xsl:value-of select="army_enemy/plasma_cannon/drops"/></xsl:when>
                    <xsl:otherwise>-</xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>