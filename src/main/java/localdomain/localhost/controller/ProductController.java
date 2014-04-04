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

package localdomain.localhost.controller;

import localdomain.localhost.domain.Product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:felix@cloudbees.com">Félix Belzunce Arcos</a>
*/

@Controller
public class ProductController {

    @PersistenceContext
    private EntityManager em;

    @RequestMapping(value = "*", method = RequestMethod.GET)
    public String listProducts(Map<String, Object> map) {

        List<Product> productlist = em.createQuery("from Product").getResultList();
        map.put("product", new Product());
        map.put("productlist", productlist);

        return "index";
    }
}
