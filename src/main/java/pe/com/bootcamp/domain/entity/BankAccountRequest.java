package pe.com.bootcamp.domain.entity;

import pe.com.bootcamp.domain.aggregate.BankAccount;

public class BankAccountRequest extends BankAccount {
	public BankAccount copy() throws CloneNotSupportedException {
		return (BankAccount) super.clone();
	}
}