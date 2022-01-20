package com.tecacet.plaid.export;


import com.plaid.client.model.Category;
import com.tecacet.jflat.CSVWriter;

import java.io.IOException;
import java.util.List;

public class CategoryExporter {

    private static final String[] HEADER = {"Category Id", "Group", "Hierarchy"};
    private static final String[] PROPERTIES = {"categoryId", "group", "hierarchy"};

    public void exportCategories(List<Category> categories, String filename) throws IOException {

        CSVWriter writer = CSVWriter.writerForProperties(PROPERTIES)
                .withHeader(HEADER);
        writer.writeToFile(filename, categories);
    }
}
