package termo.component;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import termo.cp.CpEquation;
import termo.cp.DIPPR_107_Equation;
import termo.data.ExperimentalData;
import termo.data.ExperimentalDataList;
import termo.eos.alpha.Alpha;
import termo.eos.alpha.AlphaNames;
import termo.eos.alpha.annotation.AlphaParameter;
import termo.equations.Eqn101VaporPressure;
import termo.equations.Eqn10VaporPressure;
import termo.equations.EqnVaporPressure;

/**
 *
 * @author Hugo Redon Rivera
 */
@Entity
public class Component implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Component() {
    }
    
    public Component (String name){
        this.name  = name.toLowerCase();
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "component")
    private Set<ExperimentalDataList> experimentalLists = new HashSet();
    
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
   // private double enthalpyofFormationinStandardStateat298_15Kand101325Pa;
    private double absoluteEntropyinStandardStateat298_15Kand101325Pa;
    private double meltingPoint_1atm;
    private double triplePointPressure;
    private double upperFlammabilityLimit;
    private double liquidMolarVolumeat298_15K;
    private double lowerFlammabilityLimit;
    
    @AlphaParameter(alphaName = AlphaNames.StryjekAndVera)
    private double k_StryjekAndVera;
    
    @AlphaParameter(alphaName = AlphaNames.Mathias)
    private double A_Mathias;
    
    @AlphaParameter(alphaName = AlphaNames.Twu)
    private double L_Twu;
    @AlphaParameter(alphaName = AlphaNames.Twu)
    private double M_Twu;
    @AlphaParameter(alphaName = AlphaNames.Twu)
    private double N_Twu;
    
    @AlphaParameter(alphaName = AlphaNames.Mathias_Copeman)
    private double A_Mathias_Copeman;
    @AlphaParameter(alphaName = AlphaNames.Mathias_Copeman)
    private double B_Mathias_Copeman;
    @AlphaParameter(alphaName = AlphaNames.Mathias_Copeman)
    private double C_Mathias_Copeman;
    
    private double r_UNIQUAC;
    private double q_UNIQUAC;
//    private double qq_UNIQUAC;
    
    @AlphaParameter(alphaName = AlphaNames.AdachiAndLu)
    private double A_AdachiAndLu;
    @AlphaParameter(alphaName = AlphaNames.AdachiAndLu)
    private double B_AdachiAndLu;

    @AlphaParameter(alphaName = AlphaNames.Soave2)
    private double A_Soave;
    @AlphaParameter(alphaName = AlphaNames.Soave2)
    private double B_Soave;
    
    @AlphaParameter(alphaName = AlphaNames.MelhemEtAl)
    private double A_MelhemEtAl;
    @AlphaParameter(alphaName = AlphaNames.MelhemEtAl)
    private double B_MelhemEtAl;
    
    @AlphaParameter(alphaName = AlphaNames.AndroulakisEtAl)
    private double A_AndroulakisEtAl;
    @AlphaParameter(alphaName = AlphaNames.AndroulakisEtAl)
    private double B_AndroulakisEtAl;
    @AlphaParameter(alphaName = AlphaNames.AndroulakisEtAl)
    private double C_AndroulakisEtAl;
    
    @AlphaParameter(alphaName = AlphaNames.YuAndLu)
    private double A_YuAndLu;
    @AlphaParameter(alphaName = AlphaNames.YuAndLu)
    private double B_YuAndLu;
    @AlphaParameter(alphaName = AlphaNames.YuAndLu)
    private double C_YuAndLu;
    
    @Transient
    private CpEquation cp;
    
    private double A_dippr107Cp;
    private double B_dippr107Cp;
    private double C_dippr107Cp;
    private double D_dippr107Cp;
    private double E_dippr107Cp;
    
    private double A_PolinomialCp;
    private double B_PolinomialCp;
    private double C_PolinomialCp;
    private double D_PolinomialCp;
    private double E_PolinomialCp;
    private double F_PolinomialCp;
    
		    
    
    
    private int dipprChemID;
    private String name;
    private String casNumber;
    private String formula;
    private String smiles;
    private String Structure;
    private String family;
    private String subFamily;
    private String standardState;
    

    public ArrayList<Field> getAlphaParametersFor(Alpha alpha){
        ArrayList<Field> fields = new ArrayList(); 
        for(Field field:getClass().getDeclaredFields()){
            Class clazz = AlphaParameter.class;
            Annotation annotation = field.getAnnotation(clazz);
            if(annotation instanceof AlphaParameter ){
                AlphaParameter alphaAnnotation = (AlphaParameter)annotation;
                if(alphaAnnotation.alphaName().equals(alpha.getName()))
                fields.add(field);
            }
        }
        return fields;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.molecularWeight) ^ (Double.doubleToLongBits(this.molecularWeight) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.lowerFlammabilityLimitTemperature) ^ (Double.doubleToLongBits(this.lowerFlammabilityLimitTemperature) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.upperFlammabilityLimitTemperature) ^ (Double.doubleToLongBits(this.upperFlammabilityLimitTemperature) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.criticalCompressibilityFactor) ^ (Double.doubleToLongBits(this.criticalCompressibilityFactor) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.acentricFactor) ^ (Double.doubleToLongBits(this.acentricFactor) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa) ^ (Double.doubleToLongBits(this.gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.vanDerWaalsReducedVolume) ^ (Double.doubleToLongBits(this.vanDerWaalsReducedVolume) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.vanderWaalsArea) ^ (Double.doubleToLongBits(this.vanderWaalsArea) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa) ^ (Double.doubleToLongBits(this.gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.radiusofGyration) ^ (Double.doubleToLongBits(this.radiusofGyration) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.solubilityParameterat298_15K) ^ (Double.doubleToLongBits(this.solubilityParameterat298_15K) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.dipoleMoment) ^ (Double.doubleToLongBits(this.dipoleMoment) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.triplePointTemperature) ^ (Double.doubleToLongBits(this.triplePointTemperature) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.criticalTemperature) ^ (Double.doubleToLongBits(this.criticalTemperature) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.enthalpyofFormationofIdealgasat298_15Kand101325Pa) ^ (Double.doubleToLongBits(this.enthalpyofFormationofIdealgasat298_15Kand101325Pa) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.absoluteEntropyofIdealGasat298_15Kand101325Pa) ^ (Double.doubleToLongBits(this.absoluteEntropyofIdealGasat298_15Kand101325Pa) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.netEnthalpyofCombustionStandardState298_15K) ^ (Double.doubleToLongBits(this.netEnthalpyofCombustionStandardState298_15K) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.criticalPressure) ^ (Double.doubleToLongBits(this.criticalPressure) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.normalBoilingPoint_1atm) ^ (Double.doubleToLongBits(this.normalBoilingPoint_1atm) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.enthalpyofFusionatMeltingPoint) ^ (Double.doubleToLongBits(this.enthalpyofFusionatMeltingPoint) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.criticalVolume) ^ (Double.doubleToLongBits(this.criticalVolume) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.enthalpyorHeatofSublimation) ^ (Double.doubleToLongBits(this.enthalpyorHeatofSublimation) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.dielectricConstant) ^ (Double.doubleToLongBits(this.dielectricConstant) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.refractiveIndexat298_15K) ^ (Double.doubleToLongBits(this.refractiveIndexat298_15K) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.flashPoint) ^ (Double.doubleToLongBits(this.flashPoint) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.autoIgnitionTermperature) ^ (Double.doubleToLongBits(this.autoIgnitionTermperature) >>> 32));
//        hash = 97 * hash + (int) (Double.doubleToLongBits(this.enthalpyofFormationinStandardStateat298_15Kand101325Pa) ^ (Double.doubleToLongBits(this.enthalpyofFormationinStandardStateat298_15Kand101325Pa) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.absoluteEntropyinStandardStateat298_15Kand101325Pa) ^ (Double.doubleToLongBits(this.absoluteEntropyinStandardStateat298_15Kand101325Pa) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.meltingPoint_1atm) ^ (Double.doubleToLongBits(this.meltingPoint_1atm) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.triplePointPressure) ^ (Double.doubleToLongBits(this.triplePointPressure) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.upperFlammabilityLimit) ^ (Double.doubleToLongBits(this.upperFlammabilityLimit) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.liquidMolarVolumeat298_15K) ^ (Double.doubleToLongBits(this.liquidMolarVolumeat298_15K) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.lowerFlammabilityLimit) ^ (Double.doubleToLongBits(this.lowerFlammabilityLimit) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.k_StryjekAndVera) ^ (Double.doubleToLongBits(this.k_StryjekAndVera) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.L_Twu) ^ (Double.doubleToLongBits(this.L_Twu) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.M_Twu) ^ (Double.doubleToLongBits(this.M_Twu) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.N_Twu) ^ (Double.doubleToLongBits(this.N_Twu) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.A_Mathias_Copeman) ^ (Double.doubleToLongBits(this.A_Mathias_Copeman) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.B_Mathias_Copeman) ^ (Double.doubleToLongBits(this.B_Mathias_Copeman) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.C_Mathias_Copeman) ^ (Double.doubleToLongBits(this.C_Mathias_Copeman) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.r_UNIQUAC) ^ (Double.doubleToLongBits(this.r_UNIQUAC) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.q_UNIQUAC) ^ (Double.doubleToLongBits(this.q_UNIQUAC) >>> 32));
        hash = 97 * hash + Objects.hashCode(this.getCp());
        hash = 97 * hash + this.dipprChemID;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.casNumber);
        hash = 97 * hash + Objects.hashCode(this.formula);
        hash = 97 * hash + Objects.hashCode(this.smiles);
        hash = 97 * hash + Objects.hashCode(this.Structure);
        hash = 97 * hash + Objects.hashCode(this.family);
        hash = 97 * hash + Objects.hashCode(this.subFamily);
        hash = 97 * hash + Objects.hashCode(this.standardState);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Component other = (Component) obj;
        if (Double.doubleToLongBits(this.molecularWeight) != Double.doubleToLongBits(other.molecularWeight)) {
            return false;
        }
        if (Double.doubleToLongBits(this.lowerFlammabilityLimitTemperature) != Double.doubleToLongBits(other.lowerFlammabilityLimitTemperature)) {
            return false;
        }
        if (Double.doubleToLongBits(this.upperFlammabilityLimitTemperature) != Double.doubleToLongBits(other.upperFlammabilityLimitTemperature)) {
            return false;
        }
        if (Double.doubleToLongBits(this.criticalCompressibilityFactor) != Double.doubleToLongBits(other.criticalCompressibilityFactor)) {
            return false;
        }
        if (Double.doubleToLongBits(this.acentricFactor) != Double.doubleToLongBits(other.acentricFactor)) {
            return false;
        }
        if (Double.doubleToLongBits(this.gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa) != Double.doubleToLongBits(other.gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vanDerWaalsReducedVolume) != Double.doubleToLongBits(other.vanDerWaalsReducedVolume)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vanderWaalsArea) != Double.doubleToLongBits(other.vanderWaalsArea)) {
            return false;
        }
        if (Double.doubleToLongBits(this.gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa) != Double.doubleToLongBits(other.gibbsEnergyofFormationinStandardStateat298_15Kand101325Pa)) {
            return false;
        }
        if (Double.doubleToLongBits(this.radiusofGyration) != Double.doubleToLongBits(other.radiusofGyration)) {
            return false;
        }
        if (Double.doubleToLongBits(this.solubilityParameterat298_15K) != Double.doubleToLongBits(other.solubilityParameterat298_15K)) {
            return false;
        }
        if (Double.doubleToLongBits(this.dipoleMoment) != Double.doubleToLongBits(other.dipoleMoment)) {
            return false;
        }
        if (Double.doubleToLongBits(this.triplePointTemperature) != Double.doubleToLongBits(other.triplePointTemperature)) {
            return false;
        }
        if (Double.doubleToLongBits(this.criticalTemperature) != Double.doubleToLongBits(other.criticalTemperature)) {
            return false;
        }
        if (Double.doubleToLongBits(this.enthalpyofFormationofIdealgasat298_15Kand101325Pa) != Double.doubleToLongBits(other.enthalpyofFormationofIdealgasat298_15Kand101325Pa)) {
            return false;
        }
        if (Double.doubleToLongBits(this.absoluteEntropyofIdealGasat298_15Kand101325Pa) != Double.doubleToLongBits(other.absoluteEntropyofIdealGasat298_15Kand101325Pa)) {
            return false;
        }
        if (Double.doubleToLongBits(this.netEnthalpyofCombustionStandardState298_15K) != Double.doubleToLongBits(other.netEnthalpyofCombustionStandardState298_15K)) {
            return false;
        }
        if (Double.doubleToLongBits(this.criticalPressure) != Double.doubleToLongBits(other.criticalPressure)) {
            return false;
        }
        if (Double.doubleToLongBits(this.normalBoilingPoint_1atm) != Double.doubleToLongBits(other.normalBoilingPoint_1atm)) {
            return false;
        }
        if (Double.doubleToLongBits(this.enthalpyofFusionatMeltingPoint) != Double.doubleToLongBits(other.enthalpyofFusionatMeltingPoint)) {
            return false;
        }
        if (Double.doubleToLongBits(this.criticalVolume) != Double.doubleToLongBits(other.criticalVolume)) {
            return false;
        }
        if (Double.doubleToLongBits(this.enthalpyorHeatofSublimation) != Double.doubleToLongBits(other.enthalpyorHeatofSublimation)) {
            return false;
        }
        if (Double.doubleToLongBits(this.dielectricConstant) != Double.doubleToLongBits(other.dielectricConstant)) {
            return false;
        }
        if (Double.doubleToLongBits(this.refractiveIndexat298_15K) != Double.doubleToLongBits(other.refractiveIndexat298_15K)) {
            return false;
        }
        if (Double.doubleToLongBits(this.flashPoint) != Double.doubleToLongBits(other.flashPoint)) {
            return false;
        }
        if (Double.doubleToLongBits(this.autoIgnitionTermperature) != Double.doubleToLongBits(other.autoIgnitionTermperature)) {
            return false;
        }
//        if (Double.doubleToLongBits(this.enthalpyofFormationinStandardStateat298_15Kand101325Pa) != Double.doubleToLongBits(other.enthalpyofFormationinStandardStateat298_15Kand101325Pa)) {
//            return false;
//        }
        if (Double.doubleToLongBits(this.absoluteEntropyinStandardStateat298_15Kand101325Pa) != Double.doubleToLongBits(other.absoluteEntropyinStandardStateat298_15Kand101325Pa)) {
            return false;
        }
        if (Double.doubleToLongBits(this.meltingPoint_1atm) != Double.doubleToLongBits(other.meltingPoint_1atm)) {
            return false;
        }
        if (Double.doubleToLongBits(this.triplePointPressure) != Double.doubleToLongBits(other.triplePointPressure)) {
            return false;
        }
        if (Double.doubleToLongBits(this.upperFlammabilityLimit) != Double.doubleToLongBits(other.upperFlammabilityLimit)) {
            return false;
        }
        if (Double.doubleToLongBits(this.liquidMolarVolumeat298_15K) != Double.doubleToLongBits(other.liquidMolarVolumeat298_15K)) {
            return false;
        }
        if (Double.doubleToLongBits(this.lowerFlammabilityLimit) != Double.doubleToLongBits(other.lowerFlammabilityLimit)) {
            return false;
        }
        if (Double.doubleToLongBits(this.k_StryjekAndVera) != Double.doubleToLongBits(other.k_StryjekAndVera)) {
            return false;
        }
        if (Double.doubleToLongBits(this.L_Twu) != Double.doubleToLongBits(other.L_Twu)) {
            return false;
        }
        if (Double.doubleToLongBits(this.M_Twu) != Double.doubleToLongBits(other.M_Twu)) {
            return false;
        }
        if (Double.doubleToLongBits(this.N_Twu) != Double.doubleToLongBits(other.N_Twu)) {
            return false;
        }
        if (Double.doubleToLongBits(this.A_Mathias_Copeman) != Double.doubleToLongBits(other.A_Mathias_Copeman)) {
            return false;
        }
        if (Double.doubleToLongBits(this.B_Mathias_Copeman) != Double.doubleToLongBits(other.B_Mathias_Copeman)) {
            return false;
        }
        if (Double.doubleToLongBits(this.C_Mathias_Copeman) != Double.doubleToLongBits(other.C_Mathias_Copeman)) {
            return false;
        }
        if (Double.doubleToLongBits(this.r_UNIQUAC) != Double.doubleToLongBits(other.r_UNIQUAC)) {
            return false;
        }
        if (Double.doubleToLongBits(this.q_UNIQUAC) != Double.doubleToLongBits(other.q_UNIQUAC)) {
            return false;
        }
        if (!Objects.equals(this.cp, other.cp)) {
            return false;
        }
        if (this.dipprChemID != other.dipprChemID) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.casNumber, other.casNumber)) {
            return false;
        }
        if (!Objects.equals(this.formula, other.formula)) {
            return false;
        }
        if (!Objects.equals(this.smiles, other.smiles)) {
            return false;
        }
        if (!Objects.equals(this.Structure, other.Structure)) {
            return false;
        }
        if (!Objects.equals(this.family, other.family)) {
            return false;
        }
        if (!Objects.equals(this.subFamily, other.subFamily)) {
            return false;
        }
        if (!Objects.equals(this.standardState, other.standardState)) {
            return false;
        }
        return true;
    }

   

   
    
  
  
    
    @Override
    public String toString(){
        return name;
    }

    /**
     * @return the Molecular Weight  [kg/kmol]
     */
    public double getMolecularWeight() {
        return molecularWeight;
    }

    /**
     * @param molecularWeight the Molecular Weight to set in [kg/kmol]
     */
    public void setMolecularWeight(double molecularWeight) {
        this.molecularWeight = molecularWeight;
    }

    /**
     * @return the Lower Flammability Limit Temperature [K]
     */
    public double getLowerFlammabilityLimitTemperature() {
        return lowerFlammabilityLimitTemperature;
    }

    /**
     * @param lowerFlammabilityLimitTemperature the Lower Flammability Limit Temperature to set in [K]
     */
    public void setLowerFlammabilityLimitTemperature(double lowerFlammabilityLimitTemperature) {
        this.lowerFlammabilityLimitTemperature = lowerFlammabilityLimitTemperature;
    }

    /**
     * @return the Upper Flammability Limit Temperature [K]
     */
    public double getUpperFlammabilityLimitTemperature() {
        return upperFlammabilityLimitTemperature;
    }

    /**
     * @param upperFlammabilityLimitTemperature the Upper Flammability Limit Temperature to set in [K]
     */
    public void setUpperFlammabilityLimitTemperature(double upperFlammabilityLimitTemperature) {
        this.upperFlammabilityLimitTemperature = upperFlammabilityLimitTemperature;
    }

    /**
     * @return the Critical Compressibility Factor
     */
    public double getCriticalCompressibilityFactor() {
        return criticalCompressibilityFactor;
    }

    /**
     * @param criticalCompressibilityFactor the Critical Compressibility Factor to set
     */
    public void setCriticalCompressibilityFactor(double criticalCompressibilityFactor) {
        this.criticalCompressibilityFactor = criticalCompressibilityFactor;
    }

    /**
     * @return the Acentric Factor
     */
    public double getAcentricFactor() {
        return acentricFactor;
    }

    /**
     * @param acentricFactor the Acentric Factor to set
     */
    public void setAcentricFactor(double acentricFactor) {
        this.acentricFactor = acentricFactor;
    }

    /**
     * @return the Gibbs Energy of Formation of Ideal Gas at 298.15[K] and 101325 [Pa] in [J/kmol]
     */
    public double getGibbsEnergyofFormationofIdealGasat298_15Kand101325Pa() {
        return gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa;
    }

    /**
     * @param gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa the Gibbs Energy of Formation of Ideal Gas at 298.15 [K] and 101325 [Pa] to set in [J/kmol]
     */
    public void setGibbsEnergyofFormationofIdealGasat298_15Kand101325Pa(double gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa) {
        this.gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa = gibbsEnergyofFormationofIdealGasat298_15Kand101325Pa;
    }

    /**
     * @return the van der Waals Reduced Volume in [m^3/kmol]
     */
    public double getVanDerWaalsReducedVolume() {
        return vanDerWaalsReducedVolume;
    }

    /**
     * @param vanDerWaalsReducedVolume the van der Waals Reduced Volume to set in [m^3/kmol]
     */
    public void setVanDerWaalsReducedVolume(double vanDerWaalsReducedVolume) {
        this.vanDerWaalsReducedVolume = vanDerWaalsReducedVolume;
    }

    /**
     * @return the van der WaalsArea in [m^2/kmol]
     */
    public double getVanderWaalsArea() {
        return vanderWaalsArea;
    }

    /**
     * @param vanderWaalsArea the van der Waals Area to set in [m^2/kmol]
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
     * @return the critical Pressure in [Pa]
     */
    public double getCriticalPressure() {
        return criticalPressure;
    }

    /**
     * @param criticalPressure the critical Pressure [Pa] to set
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

//    /**
//     * @return the enthalpyofFormationinStandardStateat298_15Kand101325Pa
//     */
//    public double getEnthalpyofFormationinStandardStateat298_15Kand101325Pa() {
//        return enthalpyofFormationinStandardStateat298_15Kand101325Pa;
//    }

//    /**
//     * @param enthalpyofFormationinStandardStateat298_15Kand101325Pa the enthalpyofFormationinStandardStateat298_15Kand101325Pa to set
//     */
//    public void setEnthalpyofFormationinStandardStateat298_15Kand101325Pa(double enthalpyofFormationinStandardStateat298_15Kand101325Pa) {
//        this.enthalpyofFormationinStandardStateat298_15Kand101325Pa = enthalpyofFormationinStandardStateat298_15Kand101325Pa;
//    }

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
    public double getK_StryjekAndVera() {
        return k_StryjekAndVera;
    }

    /**
     * @param prsvKappa the prsvKappa to set
     */
    public void setK_StryjekAndVera(double prsvKappa) {
        this.k_StryjekAndVera = prsvKappa;
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
        this.name = name.toLowerCase();
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

    /**
     * @return the L_Twu
     */
    public double getL_Twu() {
        return L_Twu;
    }

    /**
     * @param L_Twu the L_Twu to set
     */
    public void setL_Twu(double L_Twu) {
        this.L_Twu = L_Twu;
    }

    /**
     * @return the M_Twu
     */
    public double getM_Twu() {
        return M_Twu;
    }

    /**
     * @param M_Twu the M_Twu to set
     */
    public void setM_Twu(double M_Twu) {
        this.M_Twu = M_Twu;
    }

    /**
     * @return the N_Twu
     */
    public double getN_Twu() {
        return N_Twu;
    }

    /**
     * @param N_Twu the N_Twu to set
     */
    public void setN_Twu(double N_Twu) {
        this.N_Twu = N_Twu;
    }

    /**
     * @return the A_Mathias_Copeman
     */
    public double getA_Mathias_Copeman() {
        return A_Mathias_Copeman;
    }

    /**
     * @param A_Mathias_Copeman the A_Mathias_Copeman to set
     */
    public void setA_Mathias_Copeman(double A_Mathias_Copeman) {
        this.A_Mathias_Copeman = A_Mathias_Copeman;
    }

    /**
     * @return the B_Mathias_Copeman
     */
    public double getB_Mathias_Copeman() {
        return B_Mathias_Copeman;
    }

    /**
     * @param B_Mathias_Copeman the B_Mathias_Copeman to set
     */
    public void setB_Mathias_Copeman(double B_Mathias_Copeman) {
        this.B_Mathias_Copeman = B_Mathias_Copeman;
    }

    /**
     * @return the C_Mathias_Copeman
     */
    public double getC_Mathias_Copeman() {
        return C_Mathias_Copeman;
    }

    /**
     * @param C_Mathias_Copeman the C_Mathias_Copeman to set
     */
    public void setC_Mathias_Copeman(double C_Mathias_Copeman) {
        this.C_Mathias_Copeman = C_Mathias_Copeman;
    }

    /**
     * @return the r_UNIQUAC
     */
    public double getR_UNIQUAC() {
        return r_UNIQUAC;
    }

    /**
     * @param r_UNIQUAC the r_UNIQUAC to set
     */
    public void setR_UNIQUAC(double r_UNIQUAC) {
        this.r_UNIQUAC = r_UNIQUAC;
    }

    /**
     * @return the q_UNIQUAC
     */
    public double getQ_UNIQUAC() {
        return q_UNIQUAC;
    }

    /**
     * @param q_UNIQUAC the q_UNIQUAC to set
     */
    public void setQ_UNIQUAC(double q_UNIQUAC) {
        this.q_UNIQUAC = q_UNIQUAC;
    }

//    /**
//     * @return the qq_UNIQUAC
//     */
//    public double getQq_UNIQUAC() {
//        return qq_UNIQUAC;
//    }
//
//    /**
//     * @param qq_UNIQUAC the qq_UNIQUAC to set
//     */
//    public void setQq_UNIQUAC(double qq_UNIQUAC) {
//        this.qq_UNIQUAC = qq_UNIQUAC;
//    }

    public CpEquation getCp() {
	if(cp ==null){
	    cp = new DIPPR_107_Equation(this);
	}
        return cp;
    }

    public void setCp(CpEquation cp) {
        this.cp = cp;
    }

    public void setA_Mathias(double srk){
	this.A_Mathias = srk;
    }
    public double getA_Mathias() {
	return this.A_Mathias;
    }

    /**
     * @return the A_Cp
     */
    public double getA_dippr107Cp() {
	return A_dippr107Cp;
    }

    /**
     * @param A_Cp the A_Cp to set
     */
    public void setA_dippr107Cp(double A_Cp) {
	this.A_dippr107Cp = A_Cp;
    }

    /**
     * @return the B_Cp
     */
    public double getB_dippr107Cp() {
	return B_dippr107Cp;
    }

    /**
     * @param B_Cp the B_Cp to set
     */
    public void setB_dippr107Cp(double B_Cp) {
	this.B_dippr107Cp = B_Cp;
    }

    /**
     * @return the C_Cp
     */
    public double getC_dippr107Cp() {
	return C_dippr107Cp;
    }

    /**
     * @param C_Cp the C_Cp to set
     */
    public void setC_dippr107Cp(double C_Cp) {
	this.C_dippr107Cp = C_Cp;
    }

    /**
     * @return the D_Cp
     */
    public double getD_dippr107Cp() {
	return D_dippr107Cp;
    }

    /**
     * @param D_Cp the D_Cp to set
     */
    public void setD_dippr107Cp(double D_Cp) {
	this.D_dippr107Cp = D_Cp;
    }

    /**
     * @return the E_Cp
     */
    public double getE_dippr107Cp() {
	return E_dippr107Cp;
    }

    /**
     * @param E_Cp the E_Cp to set
     */
    public void setE_dippr107Cp(double E_Cp) {
	this.E_dippr107Cp = E_Cp;
    }

    /**
     * @return the A_PolinomialCp
     */
    public double getA_PolinomialCp() {
	return A_PolinomialCp;
    }

    /**
     * @param A_PolinomialCp the A_PolinomialCp to set
     */
    public void setA_PolinomialCp(double A_PolinomialCp) {
	this.A_PolinomialCp = A_PolinomialCp;
    }

    /**
     * @return the B_PolinomialCp
     */
    public double getB_PolinomialCp() {
	return B_PolinomialCp;
    }

    /**
     * @param B_PolinomialCp the B_PolinomialCp to set
     */
    public void setB_PolinomialCp(double B_PolinomialCp) {
	this.B_PolinomialCp = B_PolinomialCp;
    }

    /**
     * @return the C_PolinomialCp
     */
    public double getC_PolinomialCp() {
	return C_PolinomialCp;
    }

    /**
     * @param C_PolinomialCp the C_PolinomialCp to set
     */
    public void setC_PolinomialCp(double C_PolinomialCp) {
	this.C_PolinomialCp = C_PolinomialCp;
    }

    /**
     * @return the D_PolinomialCp
     */
    public double getD_PolinomialCp() {
	return D_PolinomialCp;
    }

    /**
     * @param D_PolinomialCp the D_PolinomialCp to set
     */
    public void setD_PolinomialCp(double D_PolinomialCp) {
	this.D_PolinomialCp = D_PolinomialCp;
    }

    /**
     * @return the E_PolinomialCp
     */
    public double getE_PolinomialCp() {
	return E_PolinomialCp;
    }

    /**
     * @param E_PolinomialCp the E_PolinomialCp to set
     */
    public void setE_PolinomialCp(double E_PolinomialCp) {
	this.E_PolinomialCp = E_PolinomialCp;
    }

    /**
     * @return the F_PolinomialCp
     */
    public double getF_PolinomialCp() {
	return F_PolinomialCp;
    }

    /**
     * @param F_PolinomialCp the F_PolinomialCp to set
     */
    public void setF_PolinomialCp(double F_PolinomialCp) {
	this.F_PolinomialCp = F_PolinomialCp;
    }


    /**
     * @return the A_AdachiAndLu
     */
    public double getA_AdachiAndLu() {
        return A_AdachiAndLu;
    }

    /**
     * @param A_AdachiAndLu the A_AdachiAndLu to set
     */
    public void setA_AdachiAndLu(double A_AdachiAndLu) {
        this.A_AdachiAndLu = A_AdachiAndLu;
    }

    /**
     * @return the B_AdachiAndLu
     */
    public double getB_AdachiAndLu() {
        return B_AdachiAndLu;
    }

    /**
     * @param B_AdachiAndLu the B_AdachiAndLu to set
     */
    public void setB_AdachiAndLu(double B_AdachiAndLu) {
        this.B_AdachiAndLu = B_AdachiAndLu;
    }

    /**
     * @return the A_Soave
     */
    public double getA_Soave() {
        return A_Soave;
    }

    /**
     * @param A_Soave the A_Soave to set
     */
    public void setA_Soave(double A_Soave) {
        this.A_Soave = A_Soave;
    }

    /**
     * @return the B_Soave
     */
    public double getB_Soave() {
        return B_Soave;
    }

    /**
     * @param B_Soave the B_Soave to set
     */
    public void setB_Soave(double B_Soave) {
        this.B_Soave = B_Soave;
    }

    /**
     * @return the A_MelhemEtAl
     */
    public double getA_MelhemEtAl() {
        return A_MelhemEtAl;
    }

    /**
     * @param A_MelhemEtAl the A_MelhemEtAl to set
     */
    public void setA_MelhemEtAl(double A_MelhemEtAl) {
        this.A_MelhemEtAl = A_MelhemEtAl;
    }

    /**
     * @return the B_MelhemEtAl
     */
    public double getB_MelhemEtAl() {
        return B_MelhemEtAl;
    }

    /**
     * @param B_MelhemEtAl the B_MelhemEtAl to set
     */
    public void setB_MelhemEtAl(double B_MelhemEtAl) {
        this.B_MelhemEtAl = B_MelhemEtAl;
    }

    /**
     * @return the A_AndroulakisEtAl
     */
    public double getA_AndroulakisEtAl() {
        return A_AndroulakisEtAl;
    }

    /**
     * @param A_AndroulakisEtAl the A_AndroulakisEtAl to set
     */
    public void setA_AndroulakisEtAl(double A_AndroulakisEtAl) {
        this.A_AndroulakisEtAl = A_AndroulakisEtAl;
    }

    /**
     * @return the B_AndroulakisEtAl
     */
    public double getB_AndroulakisEtAl() {
        return B_AndroulakisEtAl;
    }

    /**
     * @param B_AndroulakisEtAl the B_AndroulakisEtAl to set
     */
    public void setB_AndroulakisEtAl(double B_AndroulakisEtAl) {
        this.B_AndroulakisEtAl = B_AndroulakisEtAl;
    }

    /**
     * @return the C_AndroulakisEtAl
     */
    public double getC_AndroulakisEtAl() {
        return C_AndroulakisEtAl;
    }

    /**
     * @param C_AndroulakisEtAl the C_AndroulakisEtAl to set
     */
    public void setC_AndroulakisEtAl(double C_AndroulakisEtAl) {
        this.C_AndroulakisEtAl = C_AndroulakisEtAl;
    }

    /**
     * @return the A_YuAndLu
     */
    public double getA_YuAndLu() {
        return A_YuAndLu;
    }

    /**
     * @param A_YuAndLu the A_YuAndLu to set
     */
    public void setA_YuAndLu(double A_YuAndLu) {
        this.A_YuAndLu = A_YuAndLu;
    }

    /**
     * @return the B_YuAndLu
     */
    public double getB_YuAndLu() {
        return B_YuAndLu;
    }

    /**
     * @param B_YuAndLu the B_YuAndLu to set
     */
    public void setB_YuAndLu(double B_YuAndLu) {
        this.B_YuAndLu = B_YuAndLu;
    }

    /**
     * @return the C_YuAndLu
     */
    public double getC_YuAndLu() {
        return C_YuAndLu;
    }

    /**
     * @param C_YuAndLu the C_YuAndLu to set
     */
    public void setC_YuAndLu(double C_YuAndLu) {
        this.C_YuAndLu = C_YuAndLu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the experimentalLists
     */
    public Set<ExperimentalDataList> getExperimentalLists() {
        return experimentalLists;
    }

    /**
     * @param experimentalLists the experimentalLists to set
     */
    public void setExperimentalLists(Set<ExperimentalDataList> experimentalLists) {
        this.experimentalLists = experimentalLists;
    }

    public void addExperimentalDataList(ExperimentalDataList dataList) {
        if(!experimentalLists.contains(dataList)){
            dataList.setComponent(this);
            experimentalLists.add(dataList);
        }
    }
   // se debe decidir en obtener una super clase o mantener las dos ecuaciones
    @OneToOne(mappedBy = "component",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Eqn101VaporPressure eqn101VaporPressure ;
    
    @OneToOne(mappedBy = "component",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Eqn10VaporPressure eqn10VaporPressure ;
    
    
    
    public ExperimentalDataList getVaporPressureList(EqnVaporPressure eqn){
        ExperimentalDataList list = new ExperimentalDataList();
        if(eqn instanceof Eqn101VaporPressure){
            list.setName("Estimados Dippr 101 ");
        }else if(eqn instanceof Eqn10VaporPressure){
            list.setName("Estimados ecuación 10 (Antoine)");
        }
        list.setSource("Datos estimados con ecuación, parametros obtenidos de Chemsep"  );
        
        double minT = eqn.getMinTemperature();
        double maxT = eqn.getMaxTemperature();
        
        Integer n = 100;
        double pass = (maxT- minT)/n.doubleValue();
        for (int i = 0;i < n; i++ ){
            double temperature = minT + pass * i;
            double vaporPressure = eqn.vaporPressure(temperature);
            list.getList().add(new ExperimentalData(temperature, vaporPressure));
        }
        return list;
    }

    /**
     * @return the eqn101VaporPressure
     */
    public Eqn101VaporPressure getEqn101VaporPressure() {
        return eqn101VaporPressure;
    }

    /**
     * @param eqn101VaporPressure the eqn101VaporPressure to set
     */
    public void setEqn101VaporPressure(Eqn101VaporPressure eqn101VaporPressure) {
        this.eqn101VaporPressure = eqn101VaporPressure;
    }

    /**
     * @return the eqn10VaporPressure
     */
    public Eqn10VaporPressure getEqn10VaporPressure() {
        return eqn10VaporPressure;
    }

    /**
     * @param eqn10VaporPressure the eqn10VaporPressure to set
     */
    public void setEqn10VaporPressure(Eqn10VaporPressure eqn10VaporPressure) {
        this.eqn10VaporPressure = eqn10VaporPressure;
    }
    

  


    
}
