package com.alexandershtanko.quotations.data.models;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 06/09/2017.
 *         Copyright Ostrovok.ru
 */

public class QuotationEntity {
    private String s;
    private String b;
    private Integer bf;
    private String a;
    private int af;
    private String spr;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Integer getBf() {
        return bf;
    }

    public void setBf(Integer bf) {
        this.bf = bf;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getAf() {
        return af;
    }

    public void setAf(int af) {
        this.af = af;
    }

    public String getSpr() {
        return spr;
    }

    public void setSpr(String spr) {
        this.spr = spr;
    }
}
