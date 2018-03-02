package poly.haule.autoselling.Model;

/**
 * Created by HauLe on 15/11/2017.
 */

public class Products {
    private String Name_Products, Brand, Model, Price, Description, Image, Id;
    private int staDelete;


    public Products(String id, String name_Products, String brand, String model, String price, String description, String image, int staDelete) {
        Id = id;
        Name_Products = name_Products;
        Brand = brand;
        Model = model;
        Price = price;
        Description = description;
        Image = image;
        this.staDelete = staDelete;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getStaDelete() {
        return staDelete;
    }

    public void setStaDelete(int staDelete) {
        this.staDelete = staDelete;
    }

    public String getName_Products() {
        return Name_Products;
    }

    public void setName_Products(String name_Products) {
        Name_Products = name_Products;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
