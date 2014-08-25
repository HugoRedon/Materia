package termo.binaryParameter;

import java.util.HashMap;

import termo.component.Compound;

/**
 *
 * @author
 * Hugo
 */
public class InteractionParameter {
    
  private HashMap<String,Double> param = new HashMap();;
  private boolean symmetric;
    //private HashMap<ArrayList<Component>,Double> k21Value;

    public InteractionParameter(boolean isSymmetric) {
        symmetric = isSymmetric;
    }
    public InteractionParameter(){
	
    }
  
   
    public double  getValue(Compound component1,Compound component2){
         
        String key = keyFor(component1, component2);
        
        if(param.containsKey(key)){
            return param.get(key);
        }else{
            return 0;
        }
    }
    
    public String keyFor(Compound component1,Compound component2){
        return "("+component1.getName() +","+ component2.getName()+")";
    }
    
    public void setValue(Compound component1,Compound component2,double value){
        
        String key = keyFor(component1, component2);
        param.put(key, value);
         
//         this.symmetric = isSymmetric;
	  
        if(symmetric){
            
          String simetricKey = keyFor(component2, component1);
            param.put(simetricKey, value);
        }
    }

    /**
     * @return the symmetric
     */
    public boolean isSymmetric() {
        return symmetric;
    }

    /**
     * @param symmetric the symmetric to set
     */
    public void setSymmetric(boolean symmetric) {
        this.symmetric = symmetric;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((param == null) ? 0 : param.hashCode());
		result = prime * result + (symmetric ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InteractionParameter other = (InteractionParameter) obj;
		if (param == null) {
			if (other.param != null)
				return false;
		} else if (!param.equals(other.param))
			return false;
		if (symmetric != other.symmetric)
			return false;
		return true;
	}

}
