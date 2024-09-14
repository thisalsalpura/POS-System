/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public class InvoiceItem {
    
    private String employee_email;
    private long invoice_number;
    private String customer_mobile;
    private String customer_name;
    private String customer_points;
    private String product_id;
    private String product_name;
    private double product_price;
    private double buying_quantity;

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
     * @return the invoice_number
     */
    public long getInvoice_number() {
        return invoice_number;
    }

    /**
     * @param invoice_number the invoice_number to set
     */
    public void setInvoice_number(long invoice_number) {
        this.invoice_number = invoice_number;
    }

    /**
     * @return the customer_mobile
     */
    public String getCustomer_mobile() {
        return customer_mobile;
    }

    /**
     * @param customer_mobile the customer_mobile to set
     */
    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    /**
     * @return the customer_name
     */
    public String getCustomer_name() {
        return customer_name;
    }

    /**
     * @param customer_name the customer_name to set
     */
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    /**
     * @return the customer_points
     */
    public String getCustomer_points() {
        return customer_points;
    }

    /**
     * @param customer_points the customer_points to set
     */
    public void setCustomer_points(String customer_points) {
        this.customer_points = customer_points;
    }

    /**
     * @return the product_name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * @param product_name the product_name to set
     */
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /**
     * @return the product_price
     */
    public double getProduct_price() {
        return product_price;
    }

    /**
     * @param product_price the product_price to set
     */
    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    /**
     * @return the buying_quantity
     */
    public double getBuying_quantity() {
        return buying_quantity;
    }

    /**
     * @param buying_quantity the buying_quantity to set
     */
    public void setBuying_quantity(double buying_quantity) {
        this.buying_quantity = buying_quantity;
    }

    /**
     * @return the product_id
     */
    public String getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    
}
