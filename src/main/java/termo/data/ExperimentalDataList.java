/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termo.data;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Hugo
 */
@Entity
public class ExperimentalDataList implements Serializable {
    @Id
    private Long id;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private ArrayList<ExperimentalData> list;
    private String source;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the list
     */
    public ArrayList<ExperimentalData> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList<ExperimentalData> list) {
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
}
