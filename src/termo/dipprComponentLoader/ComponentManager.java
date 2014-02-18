
package termo.dipprComponentLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import termo.component.Component;
import static termo.dipprComponentLoader.DipprPropertyID.*;

/**
 *
 * @author Hugo Redon Rivera
 */
public final class ComponentManager {
    private Connection con;


    PreparedStatement propertyStatement;
    PreparedStatement chemInfoByCasNStatement;
    PreparedStatement allComponentsInfoStatement;
    PreparedStatement casNumberByNameStatement;
    
    public ComponentManager(String databaseName , String driver, String uri){
        try {
            openConnection(driver, uri);
            propertyStatement = con.prepareStatement(
                    "select    const.Const_value from "
                              + "["+databaseName+"].[dbo].[chem_Info] as chemInfo "
                    +" inner join ["+databaseName+"].[dbo].[Const_values] as const "
                            + " on chemInfo.ChemID = const.chemid "
                     + " inner join ["+databaseName+"].[dbo].x_property_definitions as propdef "
                            +"on const.propertyID = propdef.propertyid"
                    +" inner join ["+databaseName+"].[dbo].Sources as sources "
                            +" on const.SourceID = sources.SourceID "
                      +" where (chemInfo.CASN = ?) and  (sources.Acceptance = 'A') and (propdef.PropertyID = ?)");
            
            chemInfoByCasNStatement = con.prepareStatement("select  * from ["+databaseName+"].[dbo].[chem_Info] as info "
                    + " where info.CASN = ? ");
            casNumberByNameStatement = con.prepareStatement("select info.CASN from  ["+databaseName+"].[dbo].[chem_Info] as info"
                    + " where info.Name = ?");
             allComponentsInfoStatement = con.prepareStatement("select *  from ["+databaseName+"].[dbo].[chem_Info] as info");
            
        } catch (SQLException ex) {
            Logger.getLogger(ComponentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openConnection(String driver,String uri){
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(uri);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ComponentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    public double getPropertyValue(String CasNumber,String propertyID){
        try {
            propertyStatement.setString(1, CasNumber);
            propertyStatement.setString(2, propertyID);

            ResultSet rs = propertyStatement.executeQuery();
            propertyStatement.clearParameters();
            if(rs.next()){
                return rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComponentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    public Component getComponentByName(String name){
        try {        
            casNumberByNameStatement.setString(1, name);
            ResultSet rs = casNumberByNameStatement.executeQuery();
            
            if(rs.next()){
                return getComponentByCasNumber(rs.getString(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ComponentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
        
            
   
    }
    
    
    public Component getComponentByCasNumber(String CasNumber){
        Component aComponent = new Component();
        
        aComponent.setAbsoluteEntropyinStandardStateat298_15Kand101325Pa(getPropertyValue(CasNumber, absoluteEntropyinStandardStateat298_15Kand101325Pa));
        aComponent.setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(getPropertyValue(CasNumber, absoluteEntropyofIdealGasat298_15Kand101325Pa));
        aComponent.setAcentricFactor(getPropertyValue(CasNumber, acentricFactor));
        aComponent.setAutoIgnitionTermperature(getPropertyValue(CasNumber, autoIgnitionTermperature));
     
        aComponent.setCriticalCompressibilityFactor(getPropertyValue(CasNumber, criticalCompressibilityFactor));
        aComponent.setCriticalPressure(getPropertyValue(CasNumber, criticalPressure));
        aComponent.setCriticalTemperature(getPropertyValue(CasNumber, criticalTemperature));
        aComponent.setCriticalVolume(getPropertyValue(CasNumber, criticalVolume));
        aComponent.setDielectricConstant(getPropertyValue(CasNumber, dielectricConstant));
        aComponent.setDipoleMoment(getPropertyValue(CasNumber,dipoleMoment));

//        aComponent.setEnthalpyofFormationinStandardStateat298_15Kand101325Pa(getPropertyValue(CasNumber,enthalpyofFormationinStandardStateat298_15Kand101325Pa));
        aComponent.setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(getPropertyValue(CasNumber,enthalpyofFormationofIdealgasat298_15Kand101325Pa));
        aComponent.setEnthalpyofFusionatMeltingPoint(getPropertyValue(CasNumber,enthalpyofFusionatMeltingPoint));
        aComponent.setEnthalpyorHeatofSublimation(getPropertyValue(CasNumber,enthalpyorHeatofSublimation));
        aComponent.setFlashPoint(getPropertyValue(CasNumber,flashPoint));
        aComponent.setGibbsEnergyofFormationinStandardStateat298_15Kand101325Pa(getPropertyValue(CasNumber,gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa));
        aComponent.setGibbsEnergyofFormationofIdealGasat298_15Kand101325Pa(getPropertyValue(CasNumber,gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa));
        aComponent.setLiquidMolarVolumeat298_15K(getPropertyValue(CasNumber,liquidMolarVolumeat298_15K));
        aComponent.setLowerFlammabilityLimit(getPropertyValue(CasNumber,lowerFlammabilityLimit));
        aComponent.setLowerFlammabilityLimitTemperature(getPropertyValue(CasNumber,lowerFlammabilityLimitTemperature));
        aComponent.setMeltingPoint_1atm(getPropertyValue(CasNumber,meltingPoint_1atm));
        aComponent.setMolecularWeight(getPropertyValue(CasNumber,molecularWeight));

        aComponent.setNetEnthalpyofCombustionStandardState298_15K(getPropertyValue(CasNumber,netEnthalpyofCombustionStandardState298_15K));
        aComponent.setNormalBoilingPoint_1atm(getPropertyValue(CasNumber,normalBoilingPoint_1atm));

        aComponent.setRadiusofGyration(getPropertyValue(CasNumber,radiusofGyration));
        aComponent.setRefractiveIndexat298_15K(getPropertyValue(CasNumber,refractiveIndexat298_15K));

        aComponent.setSolubilityParameterat298_15K(getPropertyValue(CasNumber,solubilityParameterat298_15K));

        aComponent.setTriplePointPressure(getPropertyValue(CasNumber,triplePointPressure));
        aComponent.setTriplePointTemperature(getPropertyValue(CasNumber,triplePointTemperature));
        aComponent.setUpperFlammabilityLimit(getPropertyValue(CasNumber,upperFlammabilityLimit));
        aComponent.setUpperFlammabilityLimitTemperature(getPropertyValue(CasNumber,upperFlammabilityLimitTemperature));
        aComponent.setVanDerWaalsReducedVolume(getPropertyValue(CasNumber,vanDerWaalsReducedVolume));
        aComponent.setVanderWaalsArea(getPropertyValue(CasNumber,vanderWaalsArea));
        
        aComponent.setCasNumber(CasNumber);
        aComponent.setDipprChemID(Integer.valueOf(getFromChemInfo(CasNumber, ChemInfoColumn.ChemID)));
        aComponent.setName(getFromChemInfo(CasNumber, ChemInfoColumn.Name));
        aComponent.setStandardState(getFromChemInfo(CasNumber, ChemInfoColumn.StandardState));
        aComponent.setStructure(getFromChemInfo(CasNumber, ChemInfoColumn.Structure));
        aComponent.setSubFamily(getFromChemInfo(CasNumber, ChemInfoColumn.SubFamily));
        aComponent.setSmiles(getFromChemInfo(CasNumber, ChemInfoColumn.SMILES));
        aComponent.setFormula(getFromChemInfo(CasNumber, ChemInfoColumn.Formula));
        aComponent.setFamily(getFromChemInfo(CasNumber, ChemInfoColumn.Family));
        
        aComponent.setPrsvKappa(0);
        
        return aComponent;
    }
    
    public String getFromChemInfo(String casNumber,ChemInfoColumn columnIndex){
        try {
            chemInfoByCasNStatement.setString(1, casNumber);
            ResultSet rs =chemInfoByCasNStatement.executeQuery();       
            chemInfoByCasNStatement.clearParameters();
            if(rs.next()){
                return rs.getString(columnIndex.getColumnIndex());     
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComponentManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "not found";
    }
    
    public List<String> getAllComponentsNames(){
       List<String> allComponentsNamesList = new ArrayList<>();
        try {
            ResultSet rs = allComponentsInfoStatement.executeQuery();
            rs.next(); //first row is null in dippr801 database
            while(rs.next()){
                allComponentsNamesList.add(rs.getString(ChemInfoColumn.Name.getColumnIndex()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComponentManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
       return allComponentsNamesList;
    }
    public List<String> getAllComponentsCasNumbers(){
       List<String> allComponentsCasNumbersList = new ArrayList<>();
        try {
            ResultSet rs = allComponentsInfoStatement.executeQuery();
            rs.next(); //first row is null in dippr801 database
            while(rs.next()){
                allComponentsCasNumbersList.add(rs.getString(ChemInfoColumn.CASNumber.getColumnIndex()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComponentManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
       return allComponentsCasNumbersList;
    }
  
    
    public List<Component> getAllComponents(){
        List<Component> allComponents = new ArrayList<>();
        
        List<String> casNumbers = getAllComponentsCasNumbers();
        
        for(String casNumber: casNumbers){
            allComponents.add(getComponentByCasNumber(casNumber));
        }
        return allComponents;
    }
    
    
}

 enum ChemInfoColumn{
    ChemID(1),Name(2),Formula(3),CASNumber(4),SMILES(7),Structure(8),Family(9),SubFamily(10),StandardState(11);
    
    private int columnIndex;
    
    ChemInfoColumn(int columnIndex){
        this.columnIndex = columnIndex;
    }
    public int getColumnIndex(){
        return this.columnIndex;
    }
}