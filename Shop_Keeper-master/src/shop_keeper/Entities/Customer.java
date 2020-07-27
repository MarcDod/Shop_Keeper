/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop_keeper.Entities;

/**
 *
 * @author Julian
 */
public class Customer extends NPC{
    
    private ShopItem target;

    public enum Mentality{
        //@TODO: Fill in Mentalities
    }

    public Customer(String name, int texture, ShopItem firstTarget){
        super(name, texture, Mentality.valueOf(generateRandomMentality()).
                ordinal());
        this.target = firstTarget;
    }

    private static String generateRandomMentality(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ShopItem getTarget(){
        return target;
    }

    public void setTarget(ShopItem target){
        this.target = target;
    }
}
