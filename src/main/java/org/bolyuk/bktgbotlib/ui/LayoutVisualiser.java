package org.bolyuk.bktgbotlib.ui;

import de.vandermeer.asciitable.AsciiTable;
import org.bolyuk.bktgbotlib.ui.layouts.Layout;
import org.bolyuk.bktgbotlib.ui.views.View;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class LayoutVisualiser {
    public static String getString(Layout layout){
        AsciiTable at = new AsciiTable();

        ArrayList<ArrayList<View>> list = layout.getParsedLayout();

        at.addRule();

        AtomicInteger max_rows = new AtomicInteger();

        list.forEach(views -> {
            if(max_rows.get() < views.size())
                max_rows.set(views.size());
        });

        list.forEach(new Consumer<ArrayList<View>>() {
            @Override
            public void accept(ArrayList<View> views) {
                ArrayList<String> row = new ArrayList<>();
                views.forEach(view -> row.add(view.label));

                while (row.size()< max_rows.get())
                    row.add("(empty)");

                at.addRow(row);

                at.addRule();
            }
        });


        return at.render();
    }
}
