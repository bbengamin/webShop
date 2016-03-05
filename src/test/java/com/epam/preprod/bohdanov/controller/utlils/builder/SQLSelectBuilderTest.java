package com.epam.preprod.bohdanov.controller.utlils.builder;

import org.junit.Before;
import org.junit.Test;

import com.epam.preprod.bohdanov.utils.builder.SQLSelectBuilder;

import junit.framework.Assert;

public class SQLSelectBuilderTest {
    private final String TABLE_NAME = "table";
    private final String SELECT_PARAM = "*";
    private final String FIELD_NAME = "field";
    private final String TEST_PARAM_VALUE = "value";
    private final String TEST_SORT = "value";
    private final String TEST_ORDER = "ASC";
    private final String[] TEST_IN = { "1", "2" };
    private final Integer TEST_LIMIT_FROM = 0;
    private final Integer TEST_LIMIT_COUNT = 10;

    private final String EXPEXTED_EQUALS = "SELECT " + SELECT_PARAM + " FROM `" + TABLE_NAME + "` WHERE `" + FIELD_NAME
            + "` = ?";
    private final String EXPEXTED_OVER = "SELECT " + SELECT_PARAM + " FROM `" + TABLE_NAME + "` WHERE `" + FIELD_NAME
            + "` >= ?";
    private final String EXPEXTED_LESS = "SELECT " + SELECT_PARAM + " FROM `" + TABLE_NAME + "` WHERE `" + FIELD_NAME
            + "` <= ?";
    private final String EXPEXTED_LIMIT = "SELECT " + SELECT_PARAM + " FROM `" + TABLE_NAME + "` LIMIT ?, ?";
    private final String EXPEXTED_ORDER = "SELECT " + SELECT_PARAM + " FROM `" + TABLE_NAME + "` ORDER BY " + TEST_ORDER
            + " " + TEST_SORT;

    private final String EXPEXTED_IN = "SELECT " + SELECT_PARAM + " FROM `" + TABLE_NAME + "` WHERE `" + FIELD_NAME
            + "` IN(?,?)";
    private final String EXPEXTED_LIKE = "SELECT " + SELECT_PARAM + " FROM `" + TABLE_NAME + "` WHERE `" + FIELD_NAME
            + "` LIKE '%' ? '%'";

    private SQLSelectBuilder builder;

    @Before
    public void before() {
        builder = new SQLSelectBuilder(SELECT_PARAM, TABLE_NAME);
    }

    @Test
    public void testEquals() {
        builder.addEquals(FIELD_NAME, TEST_PARAM_VALUE);
        Assert.assertTrue(EXPEXTED_EQUALS.compareTo(builder.toString()) == 0);
        Assert.assertEquals(TEST_PARAM_VALUE, builder.getParameters().get(0));
    }

    @Test
    public void testLess() {
        builder.addLess(FIELD_NAME, TEST_PARAM_VALUE);
        Assert.assertTrue(EXPEXTED_LESS.compareTo(builder.toString()) == 0);
        Assert.assertEquals(TEST_PARAM_VALUE, builder.getParameters().get(0));
    }

    @Test
    public void testOver() {
        builder.addOver(FIELD_NAME, TEST_PARAM_VALUE);
        Assert.assertTrue(EXPEXTED_OVER.compareTo(builder.toString()) == 0);
        Assert.assertEquals(TEST_PARAM_VALUE, builder.getParameters().get(0));
    }

    @Test
    public void testLike() {
        builder.addLike(FIELD_NAME, TEST_PARAM_VALUE);
        Assert.assertTrue(EXPEXTED_LIKE.compareTo(builder.toString()) == 0);
        Assert.assertEquals(TEST_PARAM_VALUE, builder.getParameters().get(0));
    }

    @Test
    public void testLimit() {
        builder.addLimit(TEST_LIMIT_FROM, TEST_LIMIT_COUNT);
        Assert.assertTrue(EXPEXTED_LIMIT.compareTo(builder.toString()) == 0);
        Assert.assertEquals(TEST_LIMIT_FROM, builder.getParameters().get(0));
        Assert.assertEquals(TEST_LIMIT_COUNT, builder.getParameters().get(1));
    }

    @Test
    public void testIn() {
        builder.addIn(FIELD_NAME, TEST_IN);
        Assert.assertTrue(EXPEXTED_IN.compareTo(builder.toString()) == 0);
        Assert.assertEquals(TEST_IN[0], builder.getParameters().get(0));
        Assert.assertEquals(TEST_IN[1], builder.getParameters().get(1));
    }

    @Test
    public void testOrder() {
        builder.addOrder(TEST_ORDER, TEST_SORT);
        Assert.assertTrue(EXPEXTED_ORDER.compareTo(builder.toString()) == 0);
    }

    // SELECT * FROM `table` WHERE `field` = ? AND `field` <= ? AND `field` >= ?
    // AND `field` LIKE '%' ? '%' AND `field` IN(?,?) ORDER BY ASC value LIMIT
    // ?, ?
    @Test
    public void testAllMethods() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(SELECT_PARAM).append(" FROM `").append(TABLE_NAME);
        sb.append("` WHERE `").append(FIELD_NAME).append("` = ?  AND `");
        sb.append(FIELD_NAME).append("` = ?  AND `");
        sb.append(FIELD_NAME).append("` <= ?  AND `");
        sb.append(FIELD_NAME).append("` >= ?  AND `");
        sb.append(FIELD_NAME).append("` LIKE '%' ? '%' AND `");
        sb.append(FIELD_NAME).append("` IN(?,?) ");
        sb.append("ORDER BY ").append(TEST_ORDER).append(" " + TEST_SORT);
        sb.append(" LIMIT ?, ?");

        builder.addEquals(FIELD_NAME, TEST_PARAM_VALUE);
        builder.addEquals(FIELD_NAME, TEST_PARAM_VALUE);
        builder.addLess(FIELD_NAME, TEST_PARAM_VALUE);
        builder.addOver(FIELD_NAME, TEST_PARAM_VALUE);
        builder.addLike(FIELD_NAME, TEST_PARAM_VALUE);
        builder.addIn(FIELD_NAME, TEST_IN);
        builder.addOrder(TEST_ORDER, TEST_SORT);
        builder.addLimit(TEST_LIMIT_FROM, TEST_LIMIT_COUNT);
        Assert.assertTrue(sb.toString().compareTo(builder.toString()) == 0);
    }
}
