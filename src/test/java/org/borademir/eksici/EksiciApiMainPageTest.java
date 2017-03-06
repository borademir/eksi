package org.borademir.eksici;

import org.apache.log4j.Logger;
import org.borademir.eksici.conf.EksiciResourceUtil;
import org.borademir.eksici.test.EksiciApiAnonymousTests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category({EksiciApiAnonymousTests.class}) 
public class EksiciApiMainPageTest {
	
	static Logger log = Logger.getLogger(EksiciApiMainPageTest.class);

	 @Test
	 public void testProperties() throws Exception {
		log.debug(EksiciResourceUtil.getPopularTopicsUrl());
	    Assert.assertTrue(true);
	 }
	 
	 
}
