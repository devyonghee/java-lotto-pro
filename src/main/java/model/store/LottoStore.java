package model.store;

import java.util.ArrayList;
import java.util.Collection;

import model.generator.LottoGenerator;
import model.LottoPaper;
import model.LottoPapers;
import model.common.Money;
import utility.Assert;

public final class LottoStore {

	private final Money initialMoney;
	private final Money price;
	private final LottoGenerator<LottoPaper> lottoGenerator;

	private LottoStore(Money initialMoney, Money price, LottoGenerator<LottoPaper> lottoGenerator) {
		Assert.notNull(initialMoney, "'initialMoney' must not be null");
		Assert.notNull(price, "'price' must not be null");
		Assert.notNull(lottoGenerator, "'lottoGenerator' must not be null");
		this.initialMoney = initialMoney;
		this.price = price;
		this.lottoGenerator = lottoGenerator;
	}

	public static LottoStore of(Money initialMoney, Money price, LottoGenerator<LottoPaper> lottoGenerator) {
		return new LottoStore(initialMoney, price, lottoGenerator);
	}

	public LottoPapers lottoPapers() {
		Customer customer = Customer.from(initialMoney);
		int purchaseCount = customer.availablePurchaseCount(price);
		customer.subtractMoney(price.multiply(purchaseCount));
		return LottoPapers.from(lottoCollection(purchaseCount));
	}

	@Override
	public String toString() {
		return "LottoStore{" +
			"initialMoney=" + initialMoney +
			", price=" + price +
			", lottoGenerator=" + lottoGenerator +
			'}';
	}

	private Collection<LottoPaper> lottoCollection(int purchaseCount) {
		Collection<LottoPaper> lottoCollection = new ArrayList<>();
		for (int i = 0; i < purchaseCount; i++) {
			lottoCollection.add(lottoGenerator.lotto());
		}
		return lottoCollection;
	}
}