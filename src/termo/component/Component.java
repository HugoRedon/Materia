package termo.component;

/**
 *
 * @author Hugo Redon Rivera
 */
public class Component {
    
    private double molecularWeight;
    private double lowerFlammabilityLimitTemperature;
    private double upperFlammabilityLimitTemperature;
    private double criticalCompressibilityFactor;
    private double acentricFactor;
    private double gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa;
    private double vanDerWaalsReducedVolume;
    private double vanderWaalsArea;
    private double gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa;
    private double radiusofGyration;
    private double solubilityParameterat298_15K;
    private double dipoleMoment;
    private double triplePointTemperature;
    private double criticalTemperature;
    private double enthalpyofFormationofIdealgasat298_15Kand101325Pa;
    private double absoluteEntropyofIdealGasat298_15Kand101325Pa;
    private double netEnthalpyofCombustionStandardState298_15K;
    private double criticalPressure;    
    private double normalBoilingPoint_1atm;
    private double enthalpyofFusionatMeltingPoint;
    private double criticalVolume;
    private double enthalpyorHeatofSublimation;
    private double dielectricConstant;
    private double refractiveIndexat298_15K;
    private double flashPoint;
    private double autoIgnitionTermperature;
    private double enthalpyofFormationinStandardStateat298_15Kand101325Pa;
    private double absoluteEntropyinStandardStateat298_15Kand101325Pa;
    private double meltingPoint_1atm;
    private double triplePointPressure;
    private double upperFlammabilityLimit;
    private double liquidMolarVolumeat298_15K;
    private double lowerFlammabilityLimit;
    
    private double prsvKappa;
    
    private int dipprChemID;
    private String name;
    private String casNumber;
    private String formula;
    private String smiles;
    private String Structure;
    private String family;
    private String subFamily;
    private String standardState;
    
    
    public boolean equals(Component aComponent){ 
       return getCasNumber().equals(aComponent.getCasNumber());
    }
    
    @Override
    public String toString(){
        return name;
    }

    /**
     * @return the molecularWeight
     */
    public double getMolecularWeight() {
        return molecularWeight;
    }

    /**
     * @param molecularWeight the molecularWeight to set
     */
    public void setMolecularWeight(double molecularWeight) {
        this.molecularWeight = molecularWeight;
    }

    /**
     * @return the lowerFlammabilityLimitTemperature
     */
    public double getLowerFlammabilityLimitTemperature() {
        return lowerFlammabilityLimitTemperature;
    }

    /**
     * @param lowerFlammabilityLimitTemperature the lowerFlammabilityLimitTemperature to set
     */
    public void setLowerFlammabilityLimitTemperature(double lowerFlammabilityLimitTemperature) {
        this.lowerFlammabilityLimitTemperature = lowerFlammabilityLimitTemperature;
    }

    /**
     * @return the upperFlammabilityLimitTemperature
     */
    public double getUpperFlammabilityLimitTemperature() {
        return upperFlammabilityLimitTemperature;
    }

    /**
     * @param upperFlammabilityLimitTemperature the upperFlammabilityLimitTemperature to set
     */
    public void setUpperFlammabilityLimitTemperature(double upperFlammabilityLimitTemperature) {
        this.upperFlammabilityLimitTemperature = upperFlammabilityLimitTemperature;
    }

    /**
     * @return the criticalCompressibilityFactor
     */
    public double getCriticalCompressibilityFactor() {
        return criticalCompressibilityFactor;
    }

    /**
     * @param criticalCompressibilityFactor the criticalCompressibilityFactor to set
     */
    public void setCriticalCompressibilityFactor(double criticalCompressibilityFactor) {
        this.criticalCompressibilityFactor = criticalCompressibilityFactor;
    }

    /**
     * @return the acentricFactor
     */
    public double getAcentricFactor() {
        return acentricFactor;
    }

    /**
     * @param acentricFactor the acentricFactor to set
     */
    public void setAcentricFactor(double acentricFactor) {
        this.acentricFactor = acentricFactor;
    }

    /**
     * @return the gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa
     */
    public double getGibbsEnergyofFormationofIdealGasat298_15Kand101325Pa() {
        return gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa;
    }

    /**
     * @param gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa the gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa to set
     */
    public void setGibbsEnergyofFormationofIdealGasat298_15Kand101325Pa(double gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa) {
        this.gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa = gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa;
    }

    /**
     * @return the vanDerWaalsReducedVolume
     */
    public double getVanDerWaalsReducedVolume() {
        return vanDerWaalsReducedVolume;
    }

    /**
     * @param vanDerWaalsReducedVolume the vanDerWaalsReducedVolume to set
     */
    public void setVanDerWaalsReducedVolume(double vanDerWaalsReducedVolume) {
        this.vanDerWaalsReducedVolume = vanDerWaalsReducedVolume;
    }

    /**
     * @return the vanderWaalsArea
     */
    public double getVanderWaalsArea() {
        return vanderWaalsArea;
    }

    /**
     * @param vanderWaalsArea the vanderWaalsArea to set
     */
    public void setVanderWaalsArea(double vanderWaalsArea) {
        this.vanderWaalsArea = vanderWaalsArea;
    }

    /**
     * @return the gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa
     */
    public double getGibbsEnergyofFormationinStandardStateat298_15Kand101325Pa() {
        return gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa;
    }

    /**
     * @param gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa the gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa to set
     */
    public void setGibbsEnergyofFormationinStandardStateat298_15Kand101325Pa(double gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa) {
        this.gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa = gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa;
    }

    /**
     * @return the radiusofGyration
     */
    public double getRadiusofGyration() {
        return radiusofGyration;
    }

    /**
     * @param radiusofGyration the radiusofGyration to set
     */
    public void setRadiusofGyration(double radiusofGyration) {
        this.radiusofGyration = radiusofGyration;
    }

    /**
     * @return the solubilityParameterat298_15K
     */
    public double getSolubilityParameterat298_15K() {
        return solubilityParameterat298_15K;
    }

    /**
     * @param solubilityParameterat298_15K the solubilityParameterat298_15K to set
     */
    public void setSolubilityParameterat298_15K(double solubilityParameterat298_15K) {
        this.solubilityParameterat298_15K = solubilityParameterat298_15K;
    }

    /**
     * @return the dipoleMoment
     */
    public double getDipoleMoment() {
        return dipoleMoment;
    }

    /**
     * @param dipoleMoment the dipoleMoment to set
     */
    public void setDipoleMoment(double dipoleMoment) {
        this.dipoleMoment = dipoleMoment;
    }

    /**
     * @return the triplePointTemperature
     */
    public double getTriplePointTemperature() {
        return triplePointTemperature;
    }

    /**
     * @param triplePointTemperature the triplePointTemperature to set
     */
    public void setTriplePointTemperature(double triplePointTemperature) {
        this.triplePointTemperature = triplePointTemperature;
    }

    /**
     * @return the criticalTemperature
     */
    public double getCriticalTemperature() {
        return criticalTemperature;
    }

    /**
     * @param criticalTemperature the criticalTemperature to set
     */
    public void setCriticalTemperature(double criticalTemperature) {
        this.criticalTemperature = criticalTemperature;
    }

    /**
     * @return the enthalpyofFormationofIdealgasat298_15Kand101325Pa
     */
    public double getEnthalpyofFormationofIdealgasat298_15Kand101325Pa() {
        return enthalpyofFormationofIdealgasat298_15Kand101325Pa;
    }

    /**
     * @param enthalpyofFormationofIdealgasat298_15Kand101325Pa the enthalpyofFormationofIdealgasat298_15Kand101325Pa to set
     */
    public void setEnthalpyofFormationofIdealgasat298_15Kand101325Pa(double enthalpyofFormationofIdealgasat298_15Kand101325Pa) {
        this.enthalpyofFormationofIdealgasat298_15Kand101325Pa = enthalpyofFormationofIdealgasat298_15Kand101325Pa;
    }

    /**
     * @return the absoluteEntropyofIdealGasat298_15Kand101325Pa
     */
    public double getAbsoluteEntropyofIdealGasat298_15Kand101325Pa() {
        return absoluteEntropyofIdealGasat298_15Kand101325Pa;
    }

    /**
     * @param absoluteEntropyofIdealGasat298_15Kand101325Pa the absoluteEntropyofIdealGasat298_15Kand101325Pa to set
     */
    public void setAbsoluteEntropyofIdealGasat298_15Kand101325Pa(double absoluteEntropyofIdealGasat298_15Kand101325Pa) {
        this.absoluteEntropyofIdealGasat298_15Kand101325Pa = absoluteEntropyofIdealGasat298_15Kand101325Pa;
    }

    /**
     * @return the netEnthalpyofCombustionStandardState298_15K
     */
    public double getNetEnthalpyofCombustionStandardState298_15K() {
        return netEnthalpyofCombustionStandardState298_15K;
    }

    /**
     * @param netEnthalpyofCombustionStandardState298_15K the netEnthalpyofCombustionStandardState298_15K to set
     */
    public void setNetEnthalpyofCombustionStandardState298_15K(double netEnthalpyofCombustionStandardState298_15K) {
        this.netEnthalpyofCombustionStandardState298_15K = netEnthalpyofCombustionStandardState298_15K;
    }

    /**
     * @return the criticalPressure
     */
    public double getCriticalPressure() {
        return criticalPressure;
    }

    /**
     * @param criticalPressure the criticalPressure to set
     */
    public void setCriticalPressure(double criticalPressure) {
        this.criticalPressure = criticalPressure;
    }

    /**
     * @return the normalBoilingPoint_1atm
     */
    public double getNormalBoilingPoint_1atm() {
        return normalBoilingPoint_1atm;
    }

    /**
     * @param normalBoilingPoint_1atm the normalBoilingPoint_1atm to set
     */
    public void setNormalBoilingPoint_1atm(double normalBoilingPoint_1atm) {
        this.normalBoilingPoint_1atm = normalBoilingPoint_1atm;
    }

    /**
     * @return the enthalpyofFusionatMeltingPoint
     */
    public double getEnthalpyofFusionatMeltingPoint() {
        return enthalpyofFusionatMeltingPoint;
    }

    /**
     * @param enthalpyofFusionatMeltingPoint the enthalpyofFusionatMeltingPoint to set
     */
    public void setEnthalpyofFusionatMeltingPoint(double enthalpyofFusionatMeltingPoint) {
        this.enthalpyofFusionatMeltingPoint = enthalpyofFusionatMeltingPoint;
    }

    /**
     * @return the criticalVolume
     */
    public double getCriticalVolume() {
        return criticalVolume;
    }

    /**
     * @param criticalVolume the criticalVolume to set
     */
    public void setCriticalVolume(double criticalVolume) {
        this.criticalVolume = criticalVolume;
    }

    /**
     * @return the enthalpyorHeatofSublimation
     */
    public double getEnthalpyorHeatofSublimation() {
        return enthalpyorHeatofSublimation;
    }

    /**
     * @param enthalpyorHeatofSublimation the enthalpyorHeatofSublimation to set
     */
    public void setEnthalpyorHeatofSublimation(double enthalpyorHeatofSublimation) {
        this.enthalpyorHeatofSublimation = enthalpyorHeatofSublimation;
    }

    /**
     * @return the dielectricConstant
     */
    public double getDielectricConstant() {
        return dielectricConstant;
    }

    /**
     * @param dielectricConstant the dielectricConstant to set
     */
    public void setDielectricConstant(double dielectricConstant) {
        this.dielectricConstant = dielectricConstant;
    }

    /**
     * @return the refractiveIndexat298_15K
     */
    public double getRefractiveIndexat298_15K() {
        return refractiveIndexat298_15K;
    }

    /**
     * @param refractiveIndexat298_15K the refractiveIndexat298_15K to set
     */
    public void setRefractiveIndexat298_15K(double refractiveIndexat298_15K) {
        this.refractiveIndexat298_15K = refractiveIndexat298_15K;
    }

    /**
     * @return the flashPoint
     */
    public double getFlashPoint() {
        return flashPoint;
    }

    /**
     * @param flashPoint the flashPoint to set
     */
    public void setFlashPoint(double flashPoint) {
        this.flashPoint = flashPoint;
    }

    /**
     * @return the autoIgnitionTermperature
     */
    public double getAutoIgnitionTermperature() {
        return autoIgnitionTermperature;
    }

    /**
     * @param autoIgnitionTermperature the autoIgnitionTermperature to set
     */
    public void setAutoIgnitionTermperature(double autoIgnitionTermperature) {
        this.autoIgnitionTermperature = autoIgnitionTermperature;
    }

    /**
     * @return the enthalpyofFormationinStandardStateat298_15Kand101325Pa
     */
    public double getEnthalpyofFormationinStandardStateat298_15Kand101325Pa() {
        return enthalpyofFormationinStandardStateat298_15Kand101325Pa;
    }

    /**
     * @param enthalpyofFormationinStandardStateat298_15Kand101325Pa the enthalpyofFormationinStandardStateat298_15Kand101325Pa to set
     */
    public void setEnthalpyofFormationinStandardStateat298_15Kand101325Pa(double enthalpyofFormationinStandardStateat298_15Kand101325Pa) {
        this.enthalpyofFormationinStandardStateat298_15Kand101325Pa = enthalpyofFormationinStandardStateat298_15Kand101325Pa;
    }

    /**
     * @return the absoluteEntropyinStandardStateat298_15Kand101325Pa
     */
    public double getAbsoluteEntropyinStandardStateat298_15Kand101325Pa() {
        return absoluteEntropyinStandardStateat298_15Kand101325Pa;
    }

    /**
     * @param absoluteEntropyinStandardStateat298_15Kand101325Pa the absoluteEntropyinStandardStateat298_15Kand101325Pa to set
     */
    public void setAbsoluteEntropyinStandardStateat298_15Kand101325Pa(double absoluteEntropyinStandardStateat298_15Kand101325Pa) {
        this.absoluteEntropyinStandardStateat298_15Kand101325Pa = absoluteEntropyinStandardStateat298_15Kand101325Pa;
    }

    /**
     * @return the meltingPoint_1atm
     */
    public double getMeltingPoint_1atm() {
        return meltingPoint_1atm;
    }

    /**
     * @param meltingPoint_1atm the meltingPoint_1atm to set
     */
    public void setMeltingPoint_1atm(double meltingPoint_1atm) {
        this.meltingPoint_1atm = meltingPoint_1atm;
    }

    /**
     * @return the triplePointPressure
     */
    public double getTriplePointPressure() {
        return triplePointPressure;
    }

    /**
     * @param triplePointPressure the triplePointPressure to set
     */
    public void setTriplePointPressure(double triplePointPressure) {
        this.triplePointPressure = triplePointPressure;
    }

    /**
     * @return the upperFlammabilityLimit
     */
    public double getUpperFlammabilityLimit() {
        return upperFlammabilityLimit;
    }

    /**
     * @param upperFlammabilityLimit the upperFlammabilityLimit to set
     */
    public void setUpperFlammabilityLimit(double upperFlammabilityLimit) {
        this.upperFlammabilityLimit = upperFlammabilityLimit;
    }

    /**
     * @return the liquidMolarVolumeat298_15K
     */
    public double getLiquidMolarVolumeat298_15K() {
        return liquidMolarVolumeat298_15K;
    }

    /**
     * @param liquidMolarVolumeat298_15K the liquidMolarVolumeat298_15K to set
     */
    public void setLiquidMolarVolumeat298_15K(double liquidMolarVolumeat298_15K) {
        this.liquidMolarVolumeat298_15K = liquidMolarVolumeat298_15K;
    }

    /**
     * @return the lowerFlammabilityLimit
     */
    public double getLowerFlammabilityLimit() {
        return lowerFlammabilityLimit;
    }

    /**
     * @param lowerFlammabilityLimit the lowerFlammabilityLimit to set
     */
    public void setLowerFlammabilityLimit(double lowerFlammabilityLimit) {
        this.lowerFlammabilityLimit = lowerFlammabilityLimit;
    }

    /**
     * @return the prsvKappa
     */
    public double getPrsvKappa() {
        return prsvKappa;
    }

    /**
     * @param prsvKappa the prsvKappa to set
     */
    public void setPrsvKappa(double prsvKappa) {
        this.prsvKappa = prsvKappa;
    }

    /**
     * @return the dipprChemID
     */
    public int getDipprChemID() {
        return dipprChemID;
    }

    /**
     * @param dipprChemID the dipprChemID to set
     */
    public void setDipprChemID(int dipprChemID) {
        this.dipprChemID = dipprChemID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the casName
     */
    public String getCasNumber() {
        return casNumber;
    }

    /**
     * @param casName the casName to set
     */
    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    /**
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * @return the smiles
     */
    public String getSmiles() {
        return smiles;
    }

    /**
     * @param smiles the smiles to set
     */
    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    /**
     * @return the Structure
     */
    public String getStructure() {
        return Structure;
    }

    /**
     * @param Structure the Structure to set
     */
    public void setStructure(String Structure) {
        this.Structure = Structure;
    }

    /**
     * @return the family
     */
    public String getFamily() {
        return family;
    }

    /**
     * @param family the family to set
     */
    public void setFamily(String family) {
        this.family = family;
    }

    /**
     * @return the subFamily
     */
    public String getSubFamily() {
        return subFamily;
    }

    /**
     * @param subFamily the subFamily to set
     */
    public void setSubFamily(String subFamily) {
        this.subFamily = subFamily;
    }

    /**
     * @return the standardState
     */
    public String getStandardState() {
        return standardState;
    }

    /**
     * @param standardState the standardState to set
     */
    public void setStandardState(String standardState) {
        this.standardState = standardState;
    }
    
}
