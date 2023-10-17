package elements;

public class Bag {
    Item bag[]; //array of items

    public void AddItem(Item item) {
        //add item to array
        bag[bag.length + 1] = item;
    }

    public void RemoveItem(int index) {
        //remove item from array
        bag[index] = null;
    }
}
