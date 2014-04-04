/*
 * Copyright 2010-2014, the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sur.snapps.budgetanalyzer.web.service;

import sur.snapps.budgetanalyzer.web.domain.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author <a href="mailto:felix@cloudbees.com">Félix Belzunce Arcos</a>
*/

@Component
public class SampleDataCreatorInitializer implements InitializingBean {
	 
	@PersistenceContext
	private EntityManager em;
   
	@Autowired
    JpaTransactionManager transactionManager;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void afterPropertiesSet() throws Exception {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            Query query = em.createQuery("select count(p) from Product p");
            long count = (Long) query.getSingleResult();
            if (count == 0) {

                logger.info("Database is empty, insert demo products");

                em.persist(new Product(
                        "Long Island Iced Tea",
                        "Type of alcoholic mixed drink made with, among other ingredients, vodka, gin, tequila, and rum"));
                em.persist(new Product(
                        "Sex on the beach",
                        "Made from vodka, peach schnapps, orange juice, and cranberry juice"));
                logger.info("Demo products inserted in the database");


            } else {
                logger.info("Products found in the database, don't insert new ones");
            }
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            try {
                transactionManager.rollback(status);
            } catch (Exception rollbackException) {
                logger.warn("Silently ignore", rollbackException);
            }
            throw e;
        }		
	}

}