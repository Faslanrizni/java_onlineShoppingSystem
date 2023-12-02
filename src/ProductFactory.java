import com.sun.org.apache.bcel.internal.generic.SWITCH;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

public class ProductFactory {
    private static ProductFactory productFactory = null;
    private ProductFactory(){}

    public static ProductFactory getInstance(){
        if (productFactory==null){
            productFactory = new ProductFactory();

        }
        return productFactory;
    }

    public Product getProduct(ProductType productType){
        switch (productType){
            case CLOTHING:
                return new Clothing();
            case ELECTRONICS:
                return new Electronic();
            default:
                return null;
        }
    }

}
