/*
 * Copyright 2017-2019 CodingApi .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codingapi.txlcn.tc.aspect;

import com.codingapi.txlcn.tc.config.TxClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * create by lorne on 2018/1/5
 */
@Aspect
@Component
@Slf4j
public class BranchResourceAspect implements Ordered {

    private final TxClientConfig txClientConfig;

    private final BranchResourceWeaver branchResourceWeaver;

    public BranchResourceAspect(TxClientConfig txClientConfig, BranchResourceWeaver branchResourceWeaver) {
        this.txClientConfig = txClientConfig;
        this.branchResourceWeaver = branchResourceWeaver;
    }


    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return branchResourceWeaver.getConnection((DataSource) point.getTarget());
    }


    @Override
    public int getOrder() {
        return txClientConfig.getResourceOrder();
    }

}
