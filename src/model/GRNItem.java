/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public class GRNItem {
    
    private String employee_email;
    private long grn_number;
    private String supplier_email;
    private String supplier_name;
    private String material_id;
    private String material_name;
    private double grn_material_qty;
    private double grn_material_price;

    /**
     * @return the employee_email
     */
    public String getEmployee_email() {
        return employee_email;
    }

    /**
     * @param employee_email the employee_email to set
     */
    public void setEmployee_email(String employee_email) {
        this.employee_email = employee_email;
    }

    /**
     * @return the grn_number
     */
    public long getGrn_number() {
        return grn_number;
    }

    /**
     * @param grn_number the grn_number to set
     */
    public void setGrn_number(long grn_number) {
        this.grn_number = grn_number;
    }

    /**
     * @return the supplier_email
     */
    public String getSupplier_email() {
        return supplier_email;
    }

    /**
     * @param supplier_email the supplier_email to set
     */
    public void setSupplier_email(String supplier_email) {
        this.supplier_email = supplier_email;
    }

    /**
     * @return the supplier_name
     */
    public String getSupplier_name() {
        return supplier_name;
    }

    /**
     * @param supplier_name the supplier_name to set
     */
    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    /**
     * @return the material_id
     */
    public String getMaterial_id() {
        return material_id;
    }

    /**
     * @param material_id the material_id to set
     */
    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    /**
     * @return the material_name
     */
    public String getMaterial_name() {
        return material_name;
    }

    /**
     * @param material_name the material_name to set
     */
    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    /**
     * @return the grn_material_qty
     */
    public double getGrn_material_qty() {
        return grn_material_qty;
    }

    /**
     * @param grn_material_qty the grn_material_qty to set
     */
    public void setGrn_material_qty(double grn_material_qty) {
        this.grn_material_qty = grn_material_qty;
    }

    /**
     * @return the grn_material_price
     */
    public double getGrn_material_price() {
        return grn_material_price;
    }

    /**
     * @param grn_material_price the grn_material_price to set
     */
    public void setGrn_material_price(double grn_material_price) {
        this.grn_material_price = grn_material_price;
    }
    
}
