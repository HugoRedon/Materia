/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import termo.component.Component;

/**
 *
 * @author Hugo
 */
@Entity
public class ExperimentalDataList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    
    
    @OneToMany(mappedBy = "dataList",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<ExperimentalData> list=new HashSet();
    private String source;
    
    @ManyToOne(cascade = CascadeType.ALL ,fetch = FetchType.EAGER,optional = false,
            targetEntity = Component.class)
    private Component component;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the list
     */
    public Set<ExperimentalData> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(Set<ExperimentalData> list) {
        this.list = list;
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
     * @return the component
     */
    public Component getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(Component component) {
        this.component = component;
    }

    public void addExperimentalData(ExperimentalData data) {
        if(!list.contains(data)){
            data.setDataList(this);
            list.add(data);
        }
    }
}
