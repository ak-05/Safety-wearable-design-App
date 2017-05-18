package com.example.siddharth.tcoe;

/**
 * Created by Akshay Uppal on 7/9/2016.
 */
public class Product {

    private int _id;
    private String _productname;
    private String _num;

    public Product(){
    }

    public Product(String productname,String num){
        this._productname = productname;
        this._num=num;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }
    public void set_num(String num)
    {
        this._num=num;
    }

    public int get_id() {
        return _id;
    }

    public String get_productname() {
        return _productname;
    }
    public String get_num()
    {
        return _num;
    }
}
