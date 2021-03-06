/*
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krad.test.document.bo;

import org.kuali.rice.krad.bo.DataObjectBase;
import org.kuali.rice.krad.data.provider.annotation.ExtensionFor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRV_ACCT_EXT")
@ExtensionFor(SimpleAccount.class)
public class SimpleAccountExtension extends DataObjectBase {

    private static final long serialVersionUID = -4862497845036765764L;

    @Id
    @OneToOne
	@JoinColumn(name = "ACCT_NUM")
    private SimpleAccount account;

	@Column(name = "ACCT_TYPE")
    private String accountTypeCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCT_TYPE", insertable=false, updatable=false)
    private AccountType accountType;

    public SimpleAccount getAccount() {
        return account;
    }

    public void setAccount(SimpleAccount account) {
        this.account = account;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
 
}
