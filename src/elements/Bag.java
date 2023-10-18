package elements;

public class Bag {                              ////REFAER HA PROMAS COM O TAMNHO DO ARRAY
    Item bag[]; //array of items
    int quantity[];

    public int ItemExists(Item item) {
        //check if item exists in array
        for(int i = 0; i < bag.length; i++){
            if(bag[i].name.equals(item.name)){
                return i;
            }
        }
        return -1;
    }

    public void AddItem(Item item) {
        int index = ItemExists(item); 
        if (index != -1) {
            //add item to array
            quantity[index]++;
        } 
        else {
            //add item to array
            bag[bag.length + 1] = item;
            quantity[bag.length + 1] = 1;
        }     
    }

    public void RemoveItem(Item item) {
        int index = ItemExists(item);

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
