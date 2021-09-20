import java.io.*;
import java.util.*;
import java.lang.*;

class stockers {
    public static class Company {
        double stockPrice;
        boolean comparison;

        public Company() {
            stockPrice = 0;
            comparison = false;
        }
    }

    public static List<Company> c;

    public static void merge(int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;

        List<Company> L = new ArrayList<Company>();
        List<Company> R = new ArrayList<Company>();
        for (int i = 0; i < n1; i++) {
            Company l = new Company();
            l.stockPrice = c.get(p + i).stockPrice;
            l.comparison = c.get(p + i).comparison;
            L.add(l);
        }
        for (int i = 0; i < n2; i++) {
            Company rt = new Company();
            rt.stockPrice = c.get(q + i + 1).stockPrice;
            rt.comparison = c.get(q + i + 1).comparison;
            R.add(rt); // +1 is necessary as here it is 0 based index
        }
        int k = p; // Start with the lower index.
        int i = 0, j = 0;
        while (i < n1 && j < n2) {
            int val = Double.compare(L.get(i).stockPrice, R.get(j).stockPrice);
            if (val <= 0) {
                c.set(k, L.get(i));
                i++;
            } else {
                c.set(k, R.get(j));
                j++;
            }
            k++;
        }
        while (i < n1) {
            c.set(k, L.get(i));
            i++;
            k++;
        }
        while (j < n2) {
            c.set(k, R.get(j));
            j++;
            k++;
        }
    }

    public static void mergeSort(int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(p, q);
            mergeSort(q + 1, r);
            merge(p, q, r);
        }
    }

    public static void menu() {
        System.out.println("Enter the operation that you want to perform");
        System.out.println("1. Display the companies stock prices in ascending order");
        System.out.println("2. Display the companies stock prices in descending order");
        System.out.println("3. Display the total no of companies for which stock prices rose today");
        System.out.println("4. Display the total no of companies for which stock prices declined today");
        System.out.println("5. Search a specific stock price");
        System.out.println("6. press 0 to exit");
        System.out.println("---------------------------------------------");
    }

    public static void display() {
        for (int i = 0; i < c.size(); i++) {
            System.out.print(c.get(i).stockPrice + " ");
        }
    }

    public static void displayReverse() {
        for (int i = c.size() - 1; i >= 0; i--) {
            System.out.print(c.get(i).stockPrice + " ");
        }
    }

    public static void find(double key) {
        int val = -1;
        for (int i = 0; i < c.size(); i++) {
            val = Double.compare(c.get(i).stockPrice, key);
            if (val == 0) {
                System.out.println("Stock of value " + key + " is present");
                break;
            }
        }
        if (val != 0) {
            System.out.println("Value not found");
        }
    }

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the no of companies ");
        int n = Integer.parseInt(br.readLine().trim());
        c = new ArrayList<Company>();
        int count = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("Enter the stock price of the company " + (i + 1));
            Company obj = new Company();
            obj.stockPrice = Double.parseDouble(br.readLine().trim());
            System.out.println("Whether company's stock price rose today compare to yesterday?");
            String bool = "true";
            obj.comparison = bool.equals(br.readLine().trim());
            if (obj.comparison) {
                count++;
            }
            c.add(obj);
        }
        mergeSort(0, n - 1);
        System.out.println();
        System.out.println("---------------------------------------------------");

        int option = -1;
        while (option != 0) {
            menu();
            option = Integer.parseInt(br.readLine().trim());
            switch (option) {
                case 1:
                    System.out.println("/n Stock prices in ascending order are : ");
                    display();
                    System.out.println();
                    System.out.println("---------------------------------------------------");
                    break;

                case 2:
                    System.out.println("/n Stock prices in descending order are : ");
                    displayReverse();
                    System.out.println();
                    System.out.println("---------------------------------------------------");
                    break;

                case 3:
                    System.out.println("Total no of companies whose stock price rose today : " + count);
                    System.out.println();
                    System.out.println("---------------------------------------------------");
                    break;

                case 4:
                    System.out.println("Total no of companies whose stock price declined today : " + (n - count));
                    System.out.println();
                    System.out.println("---------------------------------------------------");
                    break;

                case 5:
                    System.out.println("Enter key value");
                    double key = Double.parseDouble(br.readLine().trim());
                    find(key);
                    System.out.println();
                    System.out.println("---------------------------------------------------");
                    break;

                case 0:
                    System.out.println("Exited successfully");
                    break;

            }
        }
    }
}
