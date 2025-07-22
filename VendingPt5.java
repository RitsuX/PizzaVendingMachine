
package group.project;
import java.util.*;
import java.io.*;

public class VendingPt5 {
    public static Scanner input = new Scanner(System.in);
    public static double total = 0; // general variable to total all burgers ordered per program use
    
    public static ArrayList<String> orderDetails = new ArrayList<>();
       
    public static void main(String[] args) throws FileNotFoundException{
        //Get bun type information from file into arrays
        File bunFile = new File("bun.txt");
        Scanner bunScan = new Scanner (bunFile);
        bunScan.useDelimiter(",|\\n");

        ArrayList<String> bun = new ArrayList<>();
        ArrayList<Double> bunPrice = new ArrayList<>();
        ArrayList<Integer> bunqoh = new ArrayList<>();
        while(bunScan.hasNext()){
            bun.add(bunScan.next());
            bunPrice.add(bunScan.nextDouble());
            bunqoh.add(bunScan.nextInt());
            if(bunScan.hasNext())bunScan.next();
        }
        bunScan.close();
        //Get fill type information from file into arrays
        File fillFile = new File("fill.txt");
        Scanner fillscan = new Scanner(fillFile);
        fillscan.useDelimiter(",|\\n");
        
        ArrayList<String> fill = new ArrayList<>();
        ArrayList<Double> fillPrice = new ArrayList<>();
        ArrayList<Integer> fillqoh = new ArrayList<>();
        while(fillscan.hasNext()){
            fill.add(fillscan.next());
            fillPrice.add(fillscan.nextDouble());
            fillqoh.add(fillscan.nextInt());
            if(fillscan.hasNext())fillscan.next();
        }
        fillscan.close();
        //Get top type information from file into arrays
        File topFile = new File("top.txt");
        Scanner topscan = new Scanner(topFile);
        topscan.useDelimiter(",|\\n");
        
        ArrayList<String> top = new ArrayList<>();
        ArrayList<Double> topPrice = new ArrayList<>();
        ArrayList<Integer> topqoh = new ArrayList<>();
        while(topscan.hasNext()){
            top.add(topscan.next());
            topPrice.add(topscan.nextDouble());
            topqoh.add(topscan.nextInt());
            if(topscan.hasNext())topscan.next();
        }
        topscan.close();
         //Get sauce type information from file into arrays
        File sauceFile = new File("sauce.txt");
        Scanner saucescan = new Scanner(sauceFile);
        saucescan.useDelimiter(",|\\n");
        
        ArrayList<String> sauce = new ArrayList<>();
        ArrayList<Double> saucePrice = new ArrayList<>();
        ArrayList<Integer> sauceqoh = new ArrayList<>();
        while(saucescan.hasNext()){
            sauce.add(saucescan.next());
            saucePrice.add(saucescan.nextDouble());
            sauceqoh.add(saucescan.nextInt());
            if(saucescan.hasNext())saucescan.next();
        }
        saucescan.close();
        
        //main program
        char order;
        boolean repeatOrder = false;
        boolean errorCheck = true; // check user error for repeat order question
        System.out.println("Welcome to the Burger Vending Machine!");
        do {
            if (errorCheck) {
                bun(bun,bunPrice,bunqoh);
                System.out.println();
                fill(fill,fillPrice,fillqoh);
                System.out.println();
                top(top,topPrice,topqoh);
                System.out.println();
                sauce(sauce,saucePrice,sauceqoh);
                System.out.println();
            }

            // more burger question
            System.out.println("Do another order?(Y/N)");
            System.out.print("Answer: ");
            order = Character.toUpperCase(input.next().charAt(0));

            if (order != 'Y' && order != 'N') {
                System.out.println("Wrong Input.");
                repeatOrder = true;
                errorCheck = false;
            } else if (order == 'Y') {
                repeatOrder = true;
                errorCheck = true;
            } else {
                repeatOrder = false;
                errorCheck = true;
            }
        } while (repeatOrder);
        payment();
        System.out.println();
        burgerProg();
        
        //update
        update(bunFile,bun,bunPrice,bunqoh);
        update(fillFile,fill,fillPrice,fillqoh);
        update(topFile,top,topPrice,topqoh);
        update(sauceFile,sauce,saucePrice,sauceqoh);
        cusLog();
    }
    public static void cusLog() throws FileNotFoundException {
        File cuslog = new File("cuslog.txt");
        Scanner scan = new Scanner(cuslog);
        ArrayList<String> cus = new ArrayList<>();
        while(scan.hasNext()){
            cus.add(scan.next());
        }
        try(PrintWriter output = new PrintWriter(cuslog)){
            for(int i = 0; i < cus.size(); i++){
                output.print(cus.get(i));
            }
            output.println();
            output.printf("%s%5s%.2f\n",orderDetails,"$",total);
        }
    }
    public static void update (File file, ArrayList<String> item, ArrayList<Double> price, ArrayList<Integer> qoh) throws FileNotFoundException{
        try(PrintWriter output = new PrintWriter(file)){
            for(int i = 0; i < item.size(); i++){
                output.printf("%s,%.2f,%d,\n",item.get(i),price.get(i),qoh.get(i));
            }
        }
    }
    public static void bun(ArrayList<String> bun, ArrayList<Double> price, ArrayList<Integer> qoh){
        boolean bunCheck = true;
        System.out.println("Bun types:");
        do {
            // Display menu ptions for buns/wraps
            for(int i = 0; i < bun.size(); i++){
                System.out.printf("%-3d.%-12s$%-5.2fQuantity on hand = %-5d\n",i,bun.get(i),price.get(i),qoh.get(i));
            }
            
            System.out.print("Input your choice: ");
            int bunChoice = input.nextInt();
            System.out.println();

            // Check availability of chosen bun/wrap
            if(bunChoice < bun.size()){
                if(qoh.get(bunChoice)>0){
                    qoh.set(bunChoice,qoh.get(bunChoice)-1);
                    //System.out.println("check"+qoh.get(bunChoice));
                    System.out.printf("You have chosen a %s.",bun.get(bunChoice));
                    total += price.get(bunChoice);
                    bunCheck = false;
                    orderDetails.add(bun.get(bunChoice));
                }
                else{
                    System.out.printf("Sorry, %s are out of stock.\n",bun);
                }
            }
            else{
                System.out.println("Invalid input. Please try again.");
            }
        } while (bunCheck);
    }
    public static void fill(ArrayList<String> fill, ArrayList<Double> price, ArrayList<Integer> qoh){
        boolean fillCheck = true;
        boolean moreFillCheck; // to skip fillings choose part for user input error on more filling question
        System.out.println("Fillings:");
        do {
            moreFillCheck = true;
            while(moreFillCheck){
                // Display menu ptions for buns/wraps
                for(int i = 0; i < fill.size(); i++){
                    System.out.printf("%-3d.%-12s$%-5.2fQuantity on hand = %-5d\n",i,fill.get(i),price.get(i),qoh.get(i));
                }
                
                System.out.print("Input your choice: ");
                int fillingChoice = input.nextInt();
                System.out.println();
                
                // Check availability of chosen filling
                if(fillingChoice < fill.size()){
                if(qoh.get(fillingChoice)>0){
                    qoh.set(fillingChoice,qoh.get(fillingChoice)-1);
                    //System.out.println("check"+qoh.get(bunChoice));
                    System.out.printf("You have chosen a %s.",fill.get(fillingChoice));
                    total += price.get(fillingChoice);
                    moreFillCheck = false;
                    orderDetails.add(fill.get(fillingChoice));
                }
                else{
                    System.out.printf("Sorry, %s are out of stock.\n",fill.get(fillingChoice));
                }
                }
                else{
                System.out.println("Invalid input. Please try again.");
                }
            }
            do{
                // more filling question
                System.out.println("Do you want more fillings?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Answer: ");
                int moreFill = input.nextInt();
                if (moreFill == 1) {
                fillCheck = true;
                moreFillCheck = false;
            } else if (moreFill == 2) {
                fillCheck = false;
                moreFillCheck = false;
            } else {
                moreFillCheck = true;
                System.out.println("Invalid input. Please try again.");
            }
            }while(moreFillCheck);
        } while (fillCheck);
    }
    public static void top(ArrayList<String> top, ArrayList<Double> price, ArrayList<Integer> qoh){
        boolean toppingCheck = true;
        boolean moreToppingCheck; // same case as in filling method
        System.out.println("Toppings: ");
        do {
            moreToppingCheck = true;
            while(moreToppingCheck) {
                // Display menu options
                for(int i = 0; i < top.size(); i++){
                    System.out.printf("%-3d.%-12s$%-5.2fQuantity on hand = %-5d\n",i,top.get(i),price.get(i),qoh.get(i));
                }

                System.out.print("Input your choice: ");
                int toppingChoice = input.nextInt();
                System.out.println();

                // Check availability of chosen filling
                if(toppingChoice < top.size()){
                if(qoh.get(toppingChoice)>0){
                    qoh.set(toppingChoice,qoh.get(toppingChoice)-1);
                    //System.out.println("check"+qoh.get(bunChoice));
                    System.out.printf("You have chosen a %s.",top.get(toppingChoice));
                    total += price.get(toppingChoice);
                    moreToppingCheck = false;
                    orderDetails.add(top.get(toppingChoice));
                }
                else{
                    System.out.printf("Sorry, %s are out of stock.\n",top.get(toppingChoice));
                }
                }
                else{
                System.out.println("Invalid input. Please try again.");
                }
            }
            do{
                // more filling question
                System.out.println("Do you want more toppings?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Answer: ");
                int moreFill = input.nextInt();
                if (moreFill == 1){
                toppingCheck = true;
                moreToppingCheck = false;
            } else if (moreFill == 2) {
                toppingCheck = false;
                moreToppingCheck = false;
            } else {
                moreToppingCheck = true;
                System.out.println("Invalid input. Please try again.");
            }
            }while(moreToppingCheck);
        } while (toppingCheck);
    }
    public static void sauce(ArrayList<String> sauce, ArrayList<Double> price, ArrayList<Integer> qoh){
        boolean sauceCheck = true;
        boolean moreSauceCheck;
        
        System.out.println("Sauces / Dressings:");
        do {
            moreSauceCheck = true;
            while(moreSauceCheck) {
                // Display menu options for sauces/dressings
                for(int i = 0; i < sauce.size(); i++){
                    System.out.printf("%-3d.%-12s$%-5.2fQuantity on hand = %-5d\n",i,sauce.get(i),price.get(i),qoh.get(i));
                }
                
                System.out.print("Input your choice: ");
                int sauceChoice = input.nextInt();
                System.out.println();

                if(sauceChoice < sauce.size()){
                if(qoh.get(sauceChoice)>0){
                    qoh.set(sauceChoice,qoh.get(sauceChoice)-1);
                    //System.out.println("check"+qoh.get(bunChoice));
                    System.out.printf("You have chosen a %s.",sauce.get(sauceChoice));
                    total += price.get(sauceChoice);
                    moreSauceCheck = false;
                    orderDetails.add(sauce.get(sauceChoice));
                }
                else{
                    System.out.printf("Sorry, %s are out of stock.\n",sauce.get(sauceChoice));
                }
                }
                else{
                System.out.println("Invalid input. Please try again.");
                }
            }
            do{
                // more filling question
                System.out.println("Do you want more sauces?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Answer: ");
                int moreFill = input.nextInt();
                if (moreFill == 1){
                sauceCheck = true;
                moreSauceCheck = false;
            } else if (moreFill == 2) {
                sauceCheck = false;
                moreSauceCheck = false;
            } else {
                moreSauceCheck = true;
                System.out.println("Invalid input. Please try again.");
            }
            }while(moreSauceCheck);
        } while (sauceCheck);
    }
    public static void payment(){
        double balance;
        double payment = 0;
        System.out.printf("Total: $%.2f\n", total); /* insert total price */
        System.out.print("Order details: ");
        for(int i = 0; i < orderDetails.size(); i++){
            System.out.print(orderDetails.get(i));
        }
        System.out.println();
        do {
            System.out.print("Please enter payment (coins or notes accepted): ");
            payment += input.nextDouble();
            balance = payment - total;
            if (balance < 0) {
                System.out.println("Insufficient payment. Please insert more money.");
            } else {
                System.out.printf("Your balance is: $%.2f\n", balance);
                break;
            }
        } while (balance < 0);
    }
    public static void burgerProg() {
        System.out.println("Preparing ingredients...");
        wait(2000);
        System.out.println("Cooking burger...");
        wait(10000);
        System.out.println("Your burger is ready!");
        wait(500);
        System.out.println("Thank you. Have a nice day!");
    }

    // Method to create delay in executing the code
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    } 
}
