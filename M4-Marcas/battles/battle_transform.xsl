<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>
    
    <xsl:template match="/battle">
        <div class="reporte-batalla">
            <h2>BATTLE #<xsl:value-of select="@id"/> STATISTICS</h2>
            
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
                        <xsl:call-template name="generateUnitRows"/>
                    </tbody>
                </table>
            </div>
                            
            <div class="costs">
                <h3>Costs</h3>
                <div class="costs-container">
                    <div class="cost-planet">
                        <h4>Planet</h4>
                        <ul>
                            <li>Metal: <xsl:value-of select="format-number(army_planet/total_cost/metal, '#,###')"/></li>
                            <li>Deuterium: <xsl:value-of select="format-number(army_planet/total_cost/deuterium, '#,###')"/></li>
                        </ul>
                    </div>
                    <div class="cost-enemy">
                        <h4>Enemy</h4>
                        <ul>
                            <li>Metal: <xsl:value-of select="format-number(army_enemy/total_cost/metal, '#,###')"/></li>
                            <li>Deuterium: <xsl:value-of select="format-number(army_enemy/total_cost/deuterium, '#,###')"/></li>
                        </ul>
                    </div>
                </div>
            </div>
                            
            <div class="losses">
                <h3>Losses</h3>
                <div class="losses-container">
                    <div class="losses-planet">
                        <h4>Planet</h4>
                        <ul>
                            <li>Metal: <xsl:value-of select="format-number(army_planet/losses/metal, '#,###')"/></li>
                            <li>Deuterium: <xsl:value-of select="format-number(army_planet/losses/deuterium, '#,###')"/></li>
                            <li>Weighted: <xsl:value-of select="format-number(army_planet/losses/weighted, '#,###')"/></li>
                        </ul>
                    </div>
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
                            
            <div class="waste">
                <h3>Waste Generated</h3>
                <ul>
                    <li>Metal: <xsl:value-of select="format-number(waste_generated/metal, '#,###')"/></li>
                    <li>Deuterium: <xsl:value-of select="format-number(waste_generated/deuterium, '#,###')"/></li>
                </ul>
            </div>
            
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
                        <xsl:when test="winner = 'planet'">Planet</xsl:when>
                        <xsl:otherwise>Enemy</xsl:otherwise>
                    </xsl:choose>
                </p>
            </div>
        </div>
    </xsl:template>
    
    <xsl:template name="generateUnitRows">
        <!-- Light Hunter -->
        <tr>
            <td>Light Hunter</td>
            <td><xsl:value-of select="army_planet/light_hunter/units"/></td>
            <td><xsl:value-of select="army_planet/light_hunter/drops"/></td>
            <td><xsl:value-of select="army_enemy/light_hunter/units"/></td>
            <td><xsl:value-of select="army_enemy/light_hunter/drops"/></td>
        </tr>
        
        <!-- Heavy Hunter -->
        <tr>
            <td>Heavy Hunter</td>
            <td><xsl:value-of select="army_planet/heavy_hunter/units"/></td>
            <td><xsl:value-of select="army_planet/heavy_hunter/drops"/></td>
            <td><xsl:value-of select="army_enemy/heavy_hunter/units"/></td>
            <td><xsl:value-of select="army_enemy/heavy_hunter/drops"/></td>
        </tr>
        
        <!-- Battle Ship -->
        <tr>
            <td>Battle Ship</td>
            <td><xsl:value-of select="army_planet/battle_ship/units"/></td>
            <td><xsl:value-of select="army_planet/battle_ship/drops"/></td>
            <td><xsl:value-of select="army_enemy/battle_ship/units"/></td>
            <td><xsl:value-of select="army_enemy/battle_ship/drops"/></td>
        </tr>
        
        <!-- Armored Ship -->
        <tr>
            <td>Armored Ship</td>
            <td><xsl:value-of select="army_planet/armored_ship/units"/></td>
            <td><xsl:value-of select="army_planet/armored_ship/drops"/></td>
            <td><xsl:value-of select="army_enemy/armored_ship/units"/></td>
            <td><xsl:value-of select="army_enemy/armored_ship/drops"/></td>
        </tr>
        
        <!-- Missile Launcher (Planet only) -->
        <tr>
            <td>Missile Launcher</td>
            <td><xsl:value-of select="army_planet/missile_launcher/units"/></td>
            <td><xsl:value-of select="army_planet/missile_launcher/drops"/></td>
            <td>-</td>
            <td>-</td>
        </tr>
        
        <!-- Ion Cannon (Planet only) -->
        <tr>
            <td>Ion Cannon</td>
            <td><xsl:value-of select="army_planet/ion_cannon/units"/></td>
            <td><xsl:value-of select="army_planet/ion_cannon/drops"/></td>
            <td>-</td>
            <td>-</td>
        </tr>
        
        <!-- Plasma Cannon (Planet only) -->
        <tr>
            <td>Plasma Cannon</td>
            <td><xsl:value-of select="army_planet/plasma_cannon/units"/></td>
            <td><xsl:value-of select="army_planet/plasma_cannon/drops"/></td>
            <td>-</td>
            <td>-</td>
        </tr>
    </xsl:template>
</xsl:stylesheet>