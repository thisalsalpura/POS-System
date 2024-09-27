package model;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class customizedTable {
    
    public static void TableStyle(JTable table) {
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        table.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        
        Font customFont = new Font("JetBrains Mono ExtraBold", Font.BOLD, 11);
        JTableHeader header = table.getTableHeader();
        header.setFont(customFont);
    }

    public static class CenteredTableCellRenderer extends DefaultTableCellRenderer {
        public CenteredTableCellRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    public static void centeredValues(JTable table) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CenteredTableCellRenderer());
        }
    }
    
}
