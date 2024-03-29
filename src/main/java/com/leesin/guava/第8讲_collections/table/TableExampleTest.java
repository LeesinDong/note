package com.leesin.guava.第8讲_collections.table;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/***************************************
 * @author:Alex Wang
 * @Date:2018/1/14
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class TableExampleTest
{
    //ArrayTable
    //TreeBaseTable
    //HashBaseTable
    //ImmutableTable

    @Test
    public void test()
    {
        Table<String, String, String> table = HashBasedTable.create();
        table.put("Language", "Java", "1.8");
        table.put("Language", "Scala", "2.3");
        table.put("Database", "Oracle", "12C");
        table.put("Database", "Mysql", "7.0");
        // {Language={Java=1.8, Scala=2.3}, Database={Oracle=12C, Mysql=7.0}}
        System.out.println(table);
        Map<String, String> language = table.row("Language");
        assertThat(language.containsKey("Java"), is(true));
        // cr row 和 column 都会生成 Map<String,Map<String,String>>，两个Map中的<row, <column, value>> <column, <key, value>>
        assertThat(table.row("Language").get("Java"), equalTo("1.8"));
        Map<String, String> result = table.column("Java");
        System.out.println(result);

        // 单元格  [(Language,Java)=1.8, (Language,Scala)=2.3, (Database,Oracle)=12C, (Database,Mysql)=7.0]
        Set<Table.Cell<String, String, String>> cells = table.cellSet();
        System.out.println(cells);

    }
}