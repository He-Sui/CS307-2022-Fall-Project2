package com.proj.sustc;

import com.proj.sustc.entity.*;
import com.proj.sustc.service.*;
import com.proj.sustc.service.exception.ServiceException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTests {
    @Autowired
    private IEnterpriseService enterpriseService;
    @Autowired
    private ICenterService centerService;
    @Autowired
    private IStaffService staffService;
    @Autowired
    private IStockService stockService;
    @Autowired
    private IModelService modelService;
    @Autowired
    private IOrderService orderService;

    @Test
    public void TestAInsertAllSupplyCenter() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/center.csv"));
            Center center = new Center();
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (info[1].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    center.setName("Hong Kong, Macao and Taiwan regions of China");
                else
                    center.setName(info[1]);
                try {
                    centerService.insert(center);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void TestBInsertAllEnterprise() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/enterprise.csv"));
            bf.readLine();
            String line;
            String[] info;
            ArrayList<Enterprise> enterprises = new ArrayList<>();
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Enterprise enterprise = new Enterprise();
                enterprise.setId(Integer.valueOf(info[0]));
                enterprise.setName(info[1]);
                enterprise.setCountry(info[2]);
                if (!info[3].equals(""))
                    enterprise.setCity(info[3]);
                enterprise.setSupplyCenter(info[4]);
                if (info[4].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    enterprise.setSupplyCenter("Hong Kong, Macao and Taiwan regions of China");
                else
                    enterprise.setSupplyCenter(info[4]);
                enterprise.setIndustry(info[5]);
                enterprises.add(enterprise);
            }
            enterprises.sort(new Comparator<Enterprise>() {
                @Override
                public int compare(Enterprise o1, Enterprise o2) {
                    return Integer.compare(o1.getId(), o2.getId());
                }
            });
            for (Enterprise enterprise : enterprises)
                try {
                    enterpriseService.insert(enterprise);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TestCInsertAllStaff() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/staff.csv"));
            Staff staff = new Staff();
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                staff.setName(info[1]);
                staff.setAge(Integer.valueOf(info[2]));
                staff.setGender(info[3]);
                staff.setNumber(info[4]);
                if (info[5].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    staff.setSupplyCenter("Hong Kong, Macao and Taiwan regions of China");
                else
                    staff.setSupplyCenter(info[5]);
                staff.setPhoneNumber(info[6]);
                staff.setType(info[7]);
                try {
                    staffService.insert(staff);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TestDInsertAllProductAndModel() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/model.csv"));
            bf.readLine();
            String line;
            String[] info;
            Model model = new Model();
            Product product = new Product();
            Set<String> productNumber = new HashSet<>();
            while ((line = bf.readLine()) != null) {
                info = line.split(",");
                if (!productNumber.contains(info[1])) {
                    product.setNumber(info[1]);
                    product.setName(info[3]);
                    productNumber.add(info[1]);
                    try {
                        modelService.insertProduct(product);
                    } catch (ServiceException e) {
                        System.err.println(e.getMessage());
                    }
                }
                model.setModel(info[2]);
                model.setProductNumber(info[1]);
                model.setUnitPrice(Integer.valueOf(info[4]));
                try {
                    modelService.insertModel(model);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void TestE_in_stoke() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/in_stoke_test.csv"));
            bf.readLine();
            String line;
            String[] info;
            StockInRecord stockInRecord = new StockInRecord();
            while ((line = bf.readLine()) != null) {
                info = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (info[1].equals("\"Hong Kong, Macao and Taiwan regions of China\""))
                    stockInRecord.setSupplyCenter("Hong Kong, Macao and Taiwan regions of China");
                else
                    stockInRecord.setSupplyCenter(info[1]);
                stockInRecord.setProductModel(info[2]);
                stockInRecord.setSupplyStaff(info[3]);
                stockInRecord.setDate(new Date(info[4]));
                stockInRecord.setPurchasePrice(Integer.valueOf(info[5]));
                stockInRecord.setQuantity(Integer.valueOf(info[6]));
                try {
                    stockService.stockIn(stockInRecord);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Test
//    public void TestFPlaceOrder() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/task2_test_data_publish.csv"));
//            bf.readLine();
//            String line;
//            String[] info;
//            while ((line = bf.readLine()) != null) {
//                info = line.split(",");
//                try {
//                    if (info[7].equals(""))
//                        orderService.placeOrder(info[0], info[1], info[2], Integer.valueOf(info[3]), info[4], simpleDateFormat.parse(info[5]), simpleDateFormat.parse(info[6]), null, info[8], info[9]);
//                    else {
//                        orderService.placeOrder(info[0], info[1], info[2], Integer.valueOf(info[3]), info[4], simpleDateFormat.parse(info[5]), simpleDateFormat.parse(info[6]), simpleDateFormat.parse(info[7]), info[8], info[9]);
//                    }
//                } catch (ServiceException e) {
//                    System.err.println(e.getMessage());
//                }
//
//            }
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//    }

    @Test
    public void TestFPlaceOrder() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/task2_test_data_final_public.tsv"));
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split("\t");
                try {
                    orderService.placeOrder(info[0], info[1], info[2], Integer.valueOf(info[3]), info[4], simpleDateFormat.parse(info[5]), simpleDateFormat.parse(info[6]), simpleDateFormat.parse(info[7]), info[8]);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }

            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void TestGUpdateOrder() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/update_final_test.tsv"));
            bf.readLine();
            String line;
            String[] info;
            Orders orders = new Orders();
            while ((line = bf.readLine()) != null) {
                info = line.split("\t");
                orders.setContractNumber(info[0]);
                orders.setProductModel(info[1]);
                orders.setSalesmanNumber(info[2]);
                orders.setQuantity(Integer.valueOf(info[3]));
                orders.setEstimatedDeliveryDate(simpleDateFormat.parse(info[4]));
                if (!info[5].equals(""))
                    orders.setLodgementDate(simpleDateFormat.parse(info[5]));
                else
                    orders.setLodgementDate(null);
                try {
                    orderService.updateOrder(orders);
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void TestHDeleteOrder() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader("src/test/data/delete_final.tsv"));
            bf.readLine();
            String line;
            String[] info;
            while ((line = bf.readLine()) != null) {
                info = line.split("\t");
                try {
                    orderService.deleteOrder(info[0], info[1], Integer.valueOf(info[2]));
                } catch (ServiceException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void TestIGetAllStaffCount() {
        List<Map<String, Object>> list = staffService.getAllStaffCount();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s%-10s\n", "type", "count"));
        for (Map<String, Object> res : list) {
            sb.append(String.format("%-20s%-10s", res.get("type"), res.get("count")));
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void TestJGetContractCount() {
        System.out.println(orderService.getContractCount());
    }

    @Test
    public void TestKGetOrderCount() {
        System.out.println(orderService.getOrderCount());
    }

    @Test
    public void TestLGetNeverSoldProductCount() {
        System.out.println(stockService.getNeverSoldProductCount());
    }

    @Test
    public void TestMGetFavoriteProductModel() {
        System.out.println(orderService.getFavoriteProductModel());
    }

    @Test
    public void TestNGetAvgStockByCenter() {
        System.out.println(stockService.getAvgStockByCenter());
    }

    @Test
    public void TestOGetProductByNumber() {
        List<Map<String, Object>> list = stockService.getProductByNumber("A50L172");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-50s%-50s%-10s\n", "supply center", "product model", "quantity"));
        for (Map<String, Object> res : list)
            sb.append(String.format("%-50s%-50s%-10s\n", res.get("supply center"), res.get("product model"), res.get("quantity")));
        System.out.println(sb.toString());
    }

    @Test
    public void TestPGetContractInfo() {
        String contractNumber = "CSE0000318";
        StringBuilder sb = new StringBuilder();
        List<Map<String, Object>> list = orderService.getContractInfo(contractNumber);
        sb.append(String.format("contract number: %s\n", contractNumber));
        sb.append(String.format("enterprise: %s\n", list.get(0).get("enterprise")));
        sb.append(String.format("manager: %s\n", list.get(0).get("manager")));
        sb.append(String.format("supply center: %s\n", list.get(0).get("supply center")));
        sb.append(String.format("%-60s%-20s%-15s%-15s%-25s%-20s\n", "product model", "salesman", "quantity", "unit price", "estimate delivery date", "lodgement date"));
        boolean check = false;
        for (int i = 1; i < list.size();++i)
            sb.append(String.format("%-60s%-20s%-15s%-15s%-25s%-20s\n", list.get(i).get("product model"), list.get(i).get("salesman"), list.get(i).get("quantity"), list.get(i).get("unit price"), list.get(i).get("estimate delivery date"), list.get(i).get("lodgement date")));
        System.out.println(sb.toString());
    }
}
