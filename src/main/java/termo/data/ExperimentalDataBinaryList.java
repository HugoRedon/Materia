package termo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    
      
    @OneToMany(mappedBy = "experimentalDataList")
    private Set<ExperimentalDataBinary> list = new HashSet();
    
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
    
    

    /**
     * @return the list
     */
    public Set<ExperimentalDataBinary> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(Set<ExperimentalDataBinary> list) {
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
    
}
