package com.roshka.dtaporteria;

import com.roshka.dtaporteria.service.Scheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DtaPorteriaApplicationTests {


	@Autowired
	Scheduler scheduler;

	@Test
	void contextLoads() {
	}


	@Test
	void makedbSyncTest() throws InterruptedException {
		scheduler.makeDbSync();
		scheduler.makeFirebaseSync();

		Thread.sleep(1000000);
	}


	@Test
	void makeSyncRecords(){
		scheduler.syncRecords();
	}


}
