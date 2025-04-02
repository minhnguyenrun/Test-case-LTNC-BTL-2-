// Author: NTM

package com.myproject;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface Testcase {
    boolean run();
    boolean run_and_show(int index);
}

class GetOutputStream {
    private static ByteArrayOutputStream outputStream;
    private static PrintStream printStream;
    private static PrintStream originalOut;

    protected static void newOuput() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        originalOut = System.out;
        System.setOut(printStream);
    }

    protected static String getOutput() {
        String capturedOutput = outputStream.toString().trim();
        System.setOut(originalOut);
        return capturedOutput;
    }
}

abstract class MyTestcase extends GetOutputStream implements Testcase {
    protected static StockTickerView stockTickerView = new StockTickerView();
    protected static StockRealtimePriceView stockRealtimePriceView = new StockRealtimePriceView();
    //protected static StockAlertView stockAlertView = new StockAlertView(1001, 300);
    protected static String expect;
    protected static String output;
    @Override
    public boolean run_and_show(int index) {
        try {
            System.out.print("Testcase " + index + ": ");
            if (run()) {
                System.out.println("Pass");
                return true;
            } else {
                System.out.println("Fail");
                System.out.println("Output:");
                System.out.println(output);
                System.out.println("Expect:");
                System.out.println(expect);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error");
            return false;
        }
    }
}

class Testcase2 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "Equal";
        StockFeeder stockFeeder2 = StockFeeder.getInstance();
        StockFeeder stockFeeder3 = StockFeeder.getInstance();
        if (stockFeeder2 == stockFeeder2) output = "Equal"; else output = "Unequal";
        return expect.equals(output);
    }
}

class Testcase3 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "";
        newOuput();
        Stock stock = new Stock("Jennie", "rubyjane");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock);
        stockFeeder.registerViewer("Jennie", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase4 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when registering with Lisa";
        newOuput();
        Stock stock = new Stock("Lisa", "rockstar");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.registerViewer("Lisa", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase5 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "";
        newOuput();
        Stock stock = new Stock("Jisoo", "flower");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock);
        stockFeeder.registerViewer("Jisoo", stockTickerView);
        stockFeeder.unregisterViewer("Jisoo", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase6 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when unregistering with Rose";
        newOuput();
        Stock stock = new Stock("Rose", "APT");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock);
        stockFeeder.unregisterViewer("Rose", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase7 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when registering with BK";
        newOuput();
        Stock stock = new Stock("BK", "hcmut");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.registerViewer("BK", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase8 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when unregistering with BK";
        newOuput();
        Stock stock = new Stock("BK", "hcmut");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        StockViewer stockViewer = new StockTickerView();
        stockFeeder.unregisterViewer("BK", stockViewer);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase9 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: YG is now $501.5";
        clearData();
        newOuput();
        StockPrice firsStockPrice = new StockPrice("YG", 501, 100, 999);
        StockPrice stockPrice = new StockPrice("YG", 501.5, 100, 999);
        stockRealtimePriceView.onUpdate(firsStockPrice);
        stockRealtimePriceView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }

    private void clearData() {
        newOuput();
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.unregisterViewer("Jennie", stockTickerView);
        stockFeeder.unregisterViewer("Lisa", stockTickerView);
        stockFeeder.unregisterViewer("Jisoo", stockTickerView);
        stockFeeder.unregisterViewer("Rose", stockTickerView);
        stockFeeder.unregisterViewer("BK", stockTickerView);
        String clearOutput = getOutput();
    }
}

class Testcase10 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: Hybe is now $501.5";
        newOuput();
        StockPrice firsStockPrice = new StockPrice("Hybe", 501, 100, 999);
        StockPrice stockPrice = new StockPrice("Hybe", 501.5, 100, 999);
        stockRealtimePriceView.onUpdate(firsStockPrice);
        stockRealtimePriceView.onUpdate(stockPrice);
        stockRealtimePriceView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase11 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: FPT is now $501.5\n[REALTIME] Realtime Price Update: FPT is now $510.5";
        newOuput();
        StockPrice stockPrice2 = new StockPrice("FPT", 500.5, 100, 999);
        StockPrice stockPrice3 = new StockPrice("FPT", 510.5, 100, 999);
        StockPrice firsStockPrice = new StockPrice("FPT", 501, 100, 999);
        stockRealtimePriceView.onUpdate(firsStockPrice);
        stockRealtimePriceView.onUpdate(stockPrice2);
        stockRealtimePriceView.onUpdate(stockPrice3);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase12 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: FPT is now $501.5";
        newOuput();
        StockRealtimePriceView stockRealtimePriceView = new StockRealtimePriceView();
        StockPrice stockPrice = new StockPrice("FPT", 501.5, 100, 999);
        StockPrice firsStockPrice = new StockPrice("FPT", 501, 100, 999);
        Stock stock2 = new Stock("FPT", "FPT software");
        Stock stock3 = new Stock("Vin", "Vin");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock2);
        stockFeeder.addStock(stock3);
        stockFeeder.registerViewer("FPT", stockRealtimePriceView);
        stockFeeder.registerViewer("Vin", stockRealtimePriceView);
        stockFeeder.notify(firsStockPrice);
        stockFeeder.notify(stockPrice);
        output = getOutput();
//        clearData();

        stockFeeder.unregisterViewer("FPT", stockRealtimePriceView);
        stockFeeder.unregisterViewer("Vin", stockRealtimePriceView);

        return expect.equals(output);
    }

    private void clearData() {
        newOuput();
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.unregisterViewer("FPT", stockTickerView);
        stockFeeder.unregisterViewer("Vin", stockTickerView);
        String clearOutput = getOutput();
    }
}

class Testcase13 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: FPT is now $501.5\n[REALTIME] Realtime Price Update: FPT is now $500.5";
        newOuput();
        StockRealtimePriceView stockRealtimePriceView2 = new StockRealtimePriceView();
        StockRealtimePriceView stockRealtimePriceView3 = new StockRealtimePriceView();
        StockPrice stockPrice = new StockPrice("FPT", 501.5, 100, 999);
        StockPrice firsStockPrice = new StockPrice("FPT", 501, 100, 999);
        Stock stock2 = new Stock("FPT", "FPT software");
        Stock stock3 = new Stock("Vin", "Vin");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock2);
        stockFeeder.addStock(stock3);
        stockFeeder.registerViewer("FPT", stockRealtimePriceView2);
        stockFeeder.registerViewer("FPT", stockRealtimePriceView3);
        stockFeeder.notify(firsStockPrice);
        stockFeeder.notify(stockPrice);
        output = getOutput();

        stockFeeder.unregisterViewer("FPT", stockRealtimePriceView2);
        stockFeeder.unregisterViewer("FPT", stockRealtimePriceView3);

//        clearData();
        return expect.equals(output);
    }

    private void clearData() {
        newOuput();
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.unregisterViewer("FPT", stockTickerView);
        stockFeeder.unregisterViewer("Vin", stockTickerView);
        String clearOutput = getOutput();
    }
}

class Testcase14 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "";
        newOuput();
        StockRealtimePriceView stockRealtimePriceView = new StockRealtimePriceView();
        StockPrice firsStockPrice = new StockPrice("FPT", 501, 100, 999);
        StockPrice stockPrice = new StockPrice("fake_FPT", 501.5, 100, 999);
        Stock stock2 = new Stock("FPT", "FPT software");
        Stock stock3 = new Stock("Vin", "Vin");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock2);
        stockFeeder.addStock(stock3);
        stockFeeder.registerViewer("FPT", stockRealtimePriceView);
        stockFeeder.registerViewer("Vin", stockRealtimePriceView);
        stockFeeder.notify(firsStockPrice);
        stockFeeder.notify(stockPrice);
        output = getOutput();
        clearData();
        return expect.equals(output);
    }

    private void clearData() {
        newOuput();
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.unregisterViewer("FPT", stockTickerView);
        stockFeeder.unregisterViewer("Vin", stockTickerView);
        String clearOutput = getOutput();
    }
}

class Testcase15 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1001, 300);
        StockPrice stockPrice = new StockPrice("VietNam", 501, 100, 5000);
        stockAlertView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase16 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $201.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1001, 300);
        StockPrice stockPrice = new StockPrice("VietNam", 201, 100, 5000);
        stockAlertView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase17 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $1201.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1001, 300);
        StockPrice stockPrice = new StockPrice("VietNam", 1201, 100, 5000);
        stockAlertView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase18 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $1201.0\n[ALERT] VietNam price changed significantly to $1205.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1001, 300);
        StockPrice stockPrice2 = new StockPrice("VietNam", 1200, 100, 5000);
        StockPrice stockPrice3 = new StockPrice("VietNam", 1200, 100, 5000);
        StockPrice stockPrice4 = new StockPrice("VietNam", 1205, 100, 5000);
        stockAlertView.onUpdate(stockPrice2);
        stockAlertView.onUpdate(stockPrice3);
        stockAlertView.onUpdate(stockPrice4);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase19 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $201.0\n[ALERT] VietNam price changed significantly to $205.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1001, 300);
        StockPrice stockPrice2 = new StockPrice("VietNam", 200, 100, 5000);
        StockPrice stockPrice3 = new StockPrice("VietNam", 200, 100, 5000);
        StockPrice stockPrice4 = new StockPrice("VietNam", 205, 100, 5000);
        stockAlertView.onUpdate(stockPrice2);
        stockAlertView.onUpdate(stockPrice3);
        stockAlertView.onUpdate(stockPrice4);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase20 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $201.0\n[ALERT] VietNam price changed significantly to $1200.0\n[ALERT] VietNam price changed significantly to $200.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1001, 300);
        StockPrice stockPrice2 = new StockPrice("VietNam", 200, 100, 5000);
        StockPrice stockPrice3 = new StockPrice("VietNam", 1200, 100, 5000);
        StockPrice stockPrice4 = new StockPrice("VietNam", 200, 100, 5000);
        stockAlertView.onUpdate(stockPrice2);
        stockAlertView.onUpdate(stockPrice3);
        stockAlertView.onUpdate(stockPrice4);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase21 extends MyTestcase {
    @Override
    public boolean run() {
        newOuput();
        expect = "VIC VNM HPG";
        String filePath = "src/resources/stocks.json";
        HosePriceFetchLib hoseLib = new HosePriceFetchLib(filePath);
        List<String> hoseStockCodes = Arrays.asList("VIC", "VNM", "HPG", "FPT");
        HoseAdapter hoseAdapter = new HoseAdapter(hoseLib, hoseStockCodes);
        List<StockPrice> list = hoseAdapter.fetch();
        for (StockPrice stockPrice : list) {
            System.out.print(stockPrice.getCode() + " ");
        }
        output = getOutput();
        return expect.equals(output);
    }
}

public class Main {
    static private List<Testcase> tc_list = new ArrayList<>();
    public static void main(String[] args) {
        int score = 1;
        tc_list.add(new Testcase2());
        tc_list.add(new Testcase3());
        tc_list.add(new Testcase4());
        tc_list.add(new Testcase5());
        tc_list.add(new Testcase6());
        tc_list.add(new Testcase7());
        tc_list.add(new Testcase8());
        tc_list.add(new Testcase9());
        tc_list.add(new Testcase10());
        tc_list.add(new Testcase11());
        tc_list.add(new Testcase12());
        tc_list.add(new Testcase13());
        tc_list.add(new Testcase15());
        tc_list.add(new Testcase15());
        tc_list.add(new Testcase16());
        tc_list.add(new Testcase17());
        tc_list.add(new Testcase18());
        tc_list.add(new Testcase19());
        tc_list.add(new Testcase20());
        tc_list.add(new Testcase21());
        for (int i = 1; i < tc_list.size(); ++i) {
            if (tc_list.get(i).run_and_show(i + 2)) ++score;
        }
        System.out.println("Score: " + score + "/" + tc_list.size());
    }
}
