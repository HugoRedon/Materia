package termo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import termo.component.Component;

/**
 *
 * @author Hugo
 */
@Entity
public class ExperimentalDataBinaryList implements Serializable{
      @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
      
      @Enumerated(EnumType.STRING)
      private ExperimentalDataBinaryType type = ExperimentalDataBinaryType.isobaric;
    
      
    @OneToMany(mappedBy = "experimentalDataList"  ,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OrderBy(value = "liquidFraction")
    private List<ExperimentalDataBinary> list = new ArrayList();
    
    @ManyToOne
    @JoinColumn
    private Component referenceComponent;
    @ManyToOne
    @JoinColumn
    private Component nonReferenceComponent;
    
    private String name;
    private String source;

    public ExperimentalDataBinaryList(String name) {
        this.name = name;
    }

    public ExperimentalDataBinaryList() {
    }
    
    public void addExperimentalDataToList(ExperimentalDataBinary data){
        data.setExperimentalDataList(this);
        list.add(data);
    }
    public void removeExperimentalDataList(ExperimentalDataBinary data){
        if(list.contains(data)){
            data.setExperimentalDataList(null);
            list.remove(data);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.list);
        hash = 79 * hash + Objects.hashCode(this.referenceComponent);
        hash = 79 * hash + Objects.hashCode(this.nonReferenceComponent);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.source);
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
        final ExperimentalDataBinaryList other = (ExperimentalDataBinaryList) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
//        if (!Objects.equals(this.list, other.list)) {
//            return false;
//        }
        if (!Objects.equals(this.referenceComponent, other.referenceComponent)) {
            return false;
        }
        if (!Objects.equals(this.nonReferenceComponent, other.nonReferenceComponent)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        return true;
    }



    
    
    /**
     * @return the list
     */
    public List<ExperimentalDataBinary> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<ExperimentalDataBinary> list) {
        this.list = list;
    }

    /**
     * @return the referenceComponent
     */
    public Component getReferenceComponent() {
        return referenceComponent;
    }

    /**
     * @param referenceComponent the referenceComponent to set
     */
    public void setReferenceComponent(Component referenceComponent) {
        this.referenceComponent = referenceComponent;
    }

    /**
     * @return the nonReferenceComponent
     */
    public Component getNonReferenceComponent() {
        return nonReferenceComponent;
    }

    /**
     * @param nonReferenceComponent the nonReferenceComponent to set
     */
    public void setNonReferenceComponent(Component nonReferenceComponent) {
        this.nonReferenceComponent = nonReferenceComponent;
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
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public ExperimentalDataBinaryType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ExperimentalDataBinaryType type) {
        this.type = type;
    }
    
}
