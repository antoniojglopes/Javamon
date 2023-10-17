package elements;

public class Bag {                              ////VER SE NAO HA PROBLEMAS COM O TAMNHO DO VETOR DE QUANTIDADE NO REMOVE ITEM
    Item bag[]; //array of items
    int quantity[];

    public boolean ItemExists(Item item) {
        //check if item exists in array
        for(int i = 0; i < bag.length; i++){
            if(bag[i].name.equals(item.name)){
                return true;
            }
        }
        return false;
    }

    public void AddItem(Item item) {
        if (ItemExists(item)) {
            //add item to array
            for(int i = 0; i < bag.length; i++){
                if(bag[i].name.equals(item.name))
                    quantity[i]++;
            }
        } 
        else {
            //add item to array
            bag[bag.length + 1] = item;
            quantity[bag.length + 1] = 1;
        }     
    }

    public void RemoveItem(int index) {
        if (quantity[index] > 1) {
            //remove item from array
            quantity[index]--;
        } 
        else {
            //remove item from array
            bag[index] = null;
            quantity[index] = 0;
        }
    }
}
