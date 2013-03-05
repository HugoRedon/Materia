
package termo.component;

/**
 *
 * @author Hugo Redon Rivera
 */

public class ComponentDeleted {
    
    private String name;
    private String family_class;
    private String chemFormula;
    private String CASNumber;
    private String UNIFACStructure;
 
    
    private double molecularWeight;
    private double normalBoilingPt;
    private double idealLiqDensity;
    
    private double criticalTemperature;  
    private double criticalPressure;
    private double criticalVolume;
    private double acentricFactor;
    
    private double dipoleMoment;
    private double radius_of_gyration;
    private double COSTALD_SRK_Acentricity;
    private double COSTALD_Volume;
    private double viscosity_CoeffA;
    private double viscosity_CoeffB;
    private double cavett_Heat_of_Vap_CoeffA;
    private double cavett_Heat_of_Vap_CoeffB;
    private double heat_of_form_25C;
    private double heat_of_comb_25C;
    private double enthalpy_basis_offset;
    private double rackett_parameter_zra;
    
    private double PRSV_kappa;
    private double kd_group_parameter;
    private double zj_eos_parameter;
    private double gs_cs_solubility_parameter;
    private double gs_cs_mol_volume;
    private double gs_cs_acentricity;
    private double UNIQUAC_R;
    private double UNIQUAC_Q;
    private double wilson_molarVolume;
    private double CN_solubility;
    private double CN_MolarVolume;
    
    private double BWRS_parameter_B0;
    private double BWRS_parameter_A0;
    private double BWRS_parameter_C0;
    private double BWRS_parameter_gamma;
    private double BWRS_parameter_b;
    private double BWRS_parameter_a;
    private double BWRS_parameter_alpha;
    private double BWRS_parameter_c;
    private double BWRS_parameter_D0;
    private double BWRS_parameter_d;
    private double BWRS_parameter_E0;
    

    public ComponentDeleted (){
        
    }
//    /**
//     * 
//     * @param id
//     * @param name
//     * @param acentricFactor
//     * @param criticalPressure atm
//     * @param criticalTemperature K
//     * @param criticalVolume L/mol
//     */
//    public Component(int id, String name,
//            double acentricFactor, 
//            double criticalPressure, double criticalTemperature,double criticalVolume){
//      this.acentricFactor = acentricFactor;
//      this.criticalPressure = criticalPressure;
//      this.criticalTemperature = criticalTemperature;
//      this.criticalVolume = criticalVolume;
//      this.name = name;
//      this.id = id;
//    }
    

    
    public double getCriticalTemperature(){
        return this.criticalTemperature;
    }
   
    public double getCriticalPressure(){
        return this.criticalPressure;
    }

    /**
     * @param criticalTemperature the critical Temperature to set in [K]
     */
    public void setCriticalTemperature(double criticalTemperature) {
        this.criticalTemperature = criticalTemperature;
    }

    /**
     * @param criticalPressure the critical Pressure to set in [atm]
     */
    public void setCriticalPressure(double criticalPressure) {
        this.criticalPressure = criticalPressure;
    }

    /**
     * @return the criticalVolume in [L/mol]
     */
    public double getCriticalVolume() {
        return criticalVolume;
    }

    /**
     * @param criticalVolume the critical Volume to set in [L/mol]
     */
    public void setCriticalVolume(double criticalVolume) {
        this.criticalVolume = criticalVolume;
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
     * @return The name of the component
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override public String toString(){
        return this.getName();
    }

    /**
     * @return The parameter of the Peng-Robinson-Stryjek-Vera equation of state as double
     */
    public double getPrsvk1() {
        return getPRSV_kappa();
    }

    /**
     * @param prsvk1 The parameter of the Peng-Robinson-Stryjek-Vera equation of state to set as double
     */
    public void setPrsvk1(double prsvk1) {
        this.setPRSV_kappa(prsvk1);
            
    }
    
   
    public boolean equals(Component aComponent){ 
       return getCASNumber().equals(aComponent.getCasNumber());
    }




    /**
     * @return the CASNumber
     */
    public String getCASNumber() {
        return CASNumber;
    }

    /**
     * @param CASNumber the CASNumber to set
     */
    public void setCASNumber(String CASNumber) {
        this.CASNumber = CASNumber;
    }

//    /**
//     * @return the liquidVolume
//     */
//    public double getLiquidVolume() {
//        return liquidVolume;
//    }
//   

    /**
     * @return the family_class
     */
    public String getFamily_class() {
        return family_class;
    }

    /**
     * @param family_class the family_class to set
     */
    public void setFamily_class(String family_class) {
        this.family_class = family_class;
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
     * @return the chemFormula
     */
    public String getChemFormula() {
        return chemFormula;
    }

    /**
     * @param chemFormula the chemFormula to set
     */
    public void setChemFormula(String chemFormula) {
        this.chemFormula = chemFormula;
    }

    /**
     * @return the UNIFACStructure
     */
    public String getUNIFACStructure() {
        return UNIFACStructure;
    }

    /**
     * @param UNIFACStructure the UNIFACStructure to set
     */
    public void setUNIFACStructure(String UNIFACStructure) {
        this.UNIFACStructure = UNIFACStructure;
    }

    /**
     * @return the normalBoilingPt
     */
    public double getNormalBoilingPt() {
        return normalBoilingPt;
    }

    /**
     * @param normalBoilingPt the normalBoilingPt to set
     */
    public void setNormalBoilingPt(double normalBoilingPt) {
        this.normalBoilingPt = normalBoilingPt;
    }

    /**
     * @return the idealLiqDensity
     */
    public double getIdealLiqDensity() {
        return idealLiqDensity;
    }

    /**
     * @param idealLiqDensity the idealLiqDensity to set
     */
    public void setIdealLiqDensity(double idealLiqDensity) {
        this.idealLiqDensity = idealLiqDensity;
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
     * @return the radius_of_gyration
     */
    public double getRadius_of_gyration() {
        return radius_of_gyration;
    }

    /**
     * @param radius_of_gyration the radius_of_gyration to set
     */
    public void setRadius_of_gyration(double radius_of_gyration) {
        this.radius_of_gyration = radius_of_gyration;
    }

    /**
     * @return the COSTALD_SRK_Acentricity
     */
    public double getCOSTALD_SRK_Acentricity() {
        return COSTALD_SRK_Acentricity;
    }

    /**
     * @param COSTALD_SRK_Acentricity the COSTALD_SRK_Acentricity to set
     */
    public void setCOSTALD_SRK_Acentricity(double COSTALD_SRK_Acentricity) {
        this.COSTALD_SRK_Acentricity = COSTALD_SRK_Acentricity;
    }

    /**
     * @return the COSTALD_Volume
     */
    public double getCOSTALD_Volume() {
        return COSTALD_Volume;
    }

    /**
     * @param COSTALD_Volume the COSTALD_Volume to set
     */
    public void setCOSTALD_Volume(double COSTALD_Volume) {
        this.COSTALD_Volume = COSTALD_Volume;
    }

    /**
     * @return the viscosity_CoeffA
     */
    public double getViscosity_CoeffA() {
        return viscosity_CoeffA;
    }

    /**
     * @param viscosity_CoeffA the viscosity_CoeffA to set
     */
    public void setViscosity_CoeffA(double viscosity_CoeffA) {
        this.viscosity_CoeffA = viscosity_CoeffA;
    }

    /**
     * @return the viscosity_CoeffB
     */
    public double getViscosity_CoeffB() {
        return viscosity_CoeffB;
    }

    /**
     * @param viscosity_CoeffB the viscosity_CoeffB to set
     */
    public void setViscosity_CoeffB(double viscosity_CoeffB) {
        this.viscosity_CoeffB = viscosity_CoeffB;
    }

    /**
     * @return the cavett_Heat_of_Vap_CoeffA
     */
    public double getCavett_Heat_of_Vap_CoeffA() {
        return cavett_Heat_of_Vap_CoeffA;
    }

    /**
     * @param cavett_Heat_of_Vap_CoeffA the cavett_Heat_of_Vap_CoeffA to set
     */
    public void setCavett_Heat_of_Vap_CoeffA(double cavett_Heat_of_Vap_CoeffA) {
        this.cavett_Heat_of_Vap_CoeffA = cavett_Heat_of_Vap_CoeffA;
    }

    /**
     * @return the cavett_Heat_of_Vap_CoeffB
     */
    public double getCavett_Heat_of_Vap_CoeffB() {
        return cavett_Heat_of_Vap_CoeffB;
    }

    /**
     * @param cavett_Heat_of_Vap_CoeffB the cavett_Heat_of_Vap_CoeffB to set
     */
    public void setCavett_Heat_of_Vap_CoeffB(double cavett_Heat_of_Vap_CoeffB) {
        this.cavett_Heat_of_Vap_CoeffB = cavett_Heat_of_Vap_CoeffB;
    }

    /**
     * @return the heat_of_form_25C
     */
    public double getHeat_of_form_25C() {
        return heat_of_form_25C;
    }

    /**
     * @param heat_of_form_25C the heat_of_form_25C to set
     */
    public void setHeat_of_form_25C(double heat_of_form_25C) {
        this.heat_of_form_25C = heat_of_form_25C;
    }

    /**
     * @return the heat_of_comb_25C
     */
    public double getHeat_of_comb_25C() {
        return heat_of_comb_25C;
    }

    /**
     * @param heat_of_comb_25C the heat_of_comb_25C to set
     */
    public void setHeat_of_comb_25C(double heat_of_comb_25C) {
        this.heat_of_comb_25C = heat_of_comb_25C;
    }

    /**
     * @return the enthalpy_basis_offset
     */
    public double getEnthalpy_basis_offset() {
        return enthalpy_basis_offset;
    }

    /**
     * @param enthalpy_basis_offset the enthalpy_basis_offset to set
     */
    public void setEnthalpy_basis_offset(double enthalpy_basis_offset) {
        this.enthalpy_basis_offset = enthalpy_basis_offset;
    }

    /**
     * @return the rackett_parameter_zra
     */
    public double getRackett_parameter_zra() {
        return rackett_parameter_zra;
    }

    /**
     * @param rackett_parameter_zra the rackett_parameter_zra to set
     */
    public void setRackett_parameter_zra(double rackett_parameter_zra) {
        this.rackett_parameter_zra = rackett_parameter_zra;
    }

    /**
     * @return the PRSV_kappa
     */
    public double getPRSV_kappa() {
        return PRSV_kappa;
    }

    /**
     * @param PRSV_kappa the PRSV_kappa to set
     */
    public void setPRSV_kappa(double PRSV_kappa) {
        this.PRSV_kappa = PRSV_kappa;
    }

    /**
     * @return the kd_group_parameter
     */
    public double getKd_group_parameter() {
        return kd_group_parameter;
    }

    /**
     * @param kd_group_parameter the kd_group_parameter to set
     */
    public void setKd_group_parameter(double kd_group_parameter) {
        this.kd_group_parameter = kd_group_parameter;
    }

    /**
     * @return the zj_eos_parameter
     */
    public double getZj_eos_parameter() {
        return zj_eos_parameter;
    }

    /**
     * @param zj_eos_parameter the zj_eos_parameter to set
     */
    public void setZj_eos_parameter(double zj_eos_parameter) {
        this.zj_eos_parameter = zj_eos_parameter;
    }

    /**
     * @return the gs_cs_solubility_parameter
     */
    public double getGs_cs_solubility_parameter() {
        return gs_cs_solubility_parameter;
    }

    /**
     * @param gs_cs_solubility_parameter the gs_cs_solubility_parameter to set
     */
    public void setGs_cs_solubility_parameter(double gs_cs_solubility_parameter) {
        this.gs_cs_solubility_parameter = gs_cs_solubility_parameter;
    }

    /**
     * @return the gs_cs_mol_volume
     */
    public double getGs_cs_mol_volume() {
        return gs_cs_mol_volume;
    }

    /**
     * @param gs_cs_mol_volume the gs_cs_mol_volume to set
     */
    public void setGs_cs_mol_volume(double gs_cs_mol_volume) {
        this.gs_cs_mol_volume = gs_cs_mol_volume;
    }

    /**
     * @return the gs_cs_acentricity
     */
    public double getGs_cs_acentricity() {
        return gs_cs_acentricity;
    }

    /**
     * @param gs_cs_acentricity the gs_cs_acentricity to set
     */
    public void setGs_cs_acentricity(double gs_cs_acentricity) {
        this.gs_cs_acentricity = gs_cs_acentricity;
    }

    /**
     * @return the UNIQUAC_R
     */
    public double getUNIQUAC_R() {
        return UNIQUAC_R;
    }

    /**
     * @param UNIQUAC_R the UNIQUAC_R to set
     */
    public void setUNIQUAC_R(double UNIQUAC_R) {
        this.UNIQUAC_R = UNIQUAC_R;
    }

    /**
     * @return the UNIQUAC_Q
     */
    public double getUNIQUAC_Q() {
        return UNIQUAC_Q;
    }

    /**
     * @param UNIQUAC_Q the UNIQUAC_Q to set
     */
    public void setUNIQUAC_Q(double UNIQUAC_Q) {
        this.UNIQUAC_Q = UNIQUAC_Q;
    }

    /**
     * @return the wilson_molarVolume
     */
    public double getWilson_molarVolume() {
        return wilson_molarVolume;
    }

    /**
     * @param wilson_molarVolume the wilson_molarVolume to set
     */
    public void setWilson_molarVolume(double wilson_molarVolume) {
        this.wilson_molarVolume = wilson_molarVolume;
    }

    /**
     * @return the CN_solubility
     */
    public double getCN_solubility() {
        return CN_solubility;
    }

    /**
     * @param CN_solubility the CN_solubility to set
     */
    public void setCN_solubility(double CN_solubility) {
        this.CN_solubility = CN_solubility;
    }

    /**
     * @return the CN_MolarVolume
     */
    public double getCN_MolarVolume() {
        return CN_MolarVolume;
    }

    /**
     * @param CN_MolarVolume the CN_MolarVolume to set
     */
    public void setCN_MolarVolume(double CN_MolarVolume) {
        this.CN_MolarVolume = CN_MolarVolume;
    }

    /**
     * @return the BWRS_parameter_B0
     */
    public double getBWRS_parameter_B0() {
        return BWRS_parameter_B0;
    }

    /**
     * @param BWRS_parameter_B0 the BWRS_parameter_B0 to set
     */
    public void setBWRS_parameter_B0(double BWRS_parameter_B0) {
        this.BWRS_parameter_B0 = BWRS_parameter_B0;
    }

    /**
     * @return the BWRS_parameter_A0
     */
    public double getBWRS_parameter_A0() {
        return BWRS_parameter_A0;
    }

    /**
     * @param BWRS_parameter_A0 the BWRS_parameter_A0 to set
     */
    public void setBWRS_parameter_A0(double BWRS_parameter_A0) {
        this.BWRS_parameter_A0 = BWRS_parameter_A0;
    }

    /**
     * @return the BWRS_parameter_C0
     */
    public double getBWRS_parameter_C0() {
        return BWRS_parameter_C0;
    }

    /**
     * @param BWRS_parameter_C0 the BWRS_parameter_C0 to set
     */
    public void setBWRS_parameter_C0(double BWRS_parameter_C0) {
        this.BWRS_parameter_C0 = BWRS_parameter_C0;
    }

    /**
     * @return the BWRS_parameter_gamma
     */
    public double getBWRS_parameter_gamma() {
        return BWRS_parameter_gamma;
    }

    /**
     * @param BWRS_parameter_gamma the BWRS_parameter_gamma to set
     */
    public void setBWRS_parameter_gamma(double BWRS_parameter_gamma) {
        this.BWRS_parameter_gamma = BWRS_parameter_gamma;
    }

    /**
     * @return the BWRS_parameter_b
     */
    public double getBWRS_parameter_b() {
        return BWRS_parameter_b;
    }

    /**
     * @param BWRS_parameter_b the BWRS_parameter_b to set
     */
    public void setBWRS_parameter_b(double BWRS_parameter_b) {
        this.BWRS_parameter_b = BWRS_parameter_b;
    }

    /**
     * @return the BWRS_parameter_a
     */
    public double getBWRS_parameter_a() {
        return BWRS_parameter_a;
    }

    /**
     * @param BWRS_parameter_a the BWRS_parameter_a to set
     */
    public void setBWRS_parameter_a(double BWRS_parameter_a) {
        this.BWRS_parameter_a = BWRS_parameter_a;
    }

    /**
     * @return the BWRS_parameter_alpha
     */
    public double getBWRS_parameter_alpha() {
        return BWRS_parameter_alpha;
    }

    /**
     * @param BWRS_parameter_alpha the BWRS_parameter_alpha to set
     */
    public void setBWRS_parameter_alpha(double BWRS_parameter_alpha) {
        this.BWRS_parameter_alpha = BWRS_parameter_alpha;
    }

    /**
     * @return the BWRS_parameter_c
     */
    public double getBWRS_parameter_c() {
        return BWRS_parameter_c;
    }

    /**
     * @param BWRS_parameter_c the BWRS_parameter_c to set
     */
    public void setBWRS_parameter_c(double BWRS_parameter_c) {
        this.BWRS_parameter_c = BWRS_parameter_c;
    }

    /**
     * @return the BWRS_parameter_D0
     */
    public double getBWRS_parameter_D0() {
        return BWRS_parameter_D0;
    }

    /**
     * @param BWRS_parameter_D0 the BWRS_parameter_D0 to set
     */
    public void setBWRS_parameter_D0(double BWRS_parameter_D0) {
        this.BWRS_parameter_D0 = BWRS_parameter_D0;
    }

    /**
     * @return the BWRS_parameter_d
     */
    public double getBWRS_parameter_d() {
        return BWRS_parameter_d;
    }

    /**
     * @param BWRS_parameter_d the BWRS_parameter_d to set
     */
    public void setBWRS_parameter_d(double BWRS_parameter_d) {
        this.BWRS_parameter_d = BWRS_parameter_d;
    }

    /**
     * @return the BWRS_parameter_E0
     */
    public double getBWRS_parameter_E0() {
        return BWRS_parameter_E0;
    }

    /**
     * @param BWRS_parameter_E0 the BWRS_parameter_E0 to set
     */
    public void setBWRS_parameter_E0(double BWRS_parameter_E0) {
        this.BWRS_parameter_E0 = BWRS_parameter_E0;
    }
}

