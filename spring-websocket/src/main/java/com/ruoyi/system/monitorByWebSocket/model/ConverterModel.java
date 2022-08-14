package com.ruoyi.system.monitorByWebSocket.model;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConverterModel implements  Comparable<ConverterModel>,Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String av;
    private String aa;
    private String bv;
    private String ba;
    private String cv;
    private String ca;
    private Integer sort;


    /**
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(@NotNull ConverterModel o) {
//            return this.name.compareTo(emp.getName());//按照 String 升序排序
        return this.sort-o.getSort();//按照 int 升序排序
    }
}
