package dao.receipt;

import bean.Receipt;
import bean.ReceiptItem;

import java.util.List;

public interface ReceiptDao {

    List<Receipt> findAll();

    int createReceipt(int cashBox_id);

    boolean addItemToReceipt(int receiptId, ReceiptItem receiptItem);

    List<ReceiptItem> getAllItemsForReceipt(int receiptId);

    boolean deleteItem(int itemId);

    boolean updateItemQuantity(int itemId, int quantity);

    ReceiptItem getReceiptItemByProduct(int receiptId, int productId);

    boolean setReceiptStatus(int receiptId, String status);

    boolean closeReceipt(int receiptId);

    int getReceiptsNumber();

    List<Receipt> findReceipts(int page, int pageSize);

}
