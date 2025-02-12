package dev.ithundxr.createnumismatics.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.base.item.DyedItemList;
import dev.ithundxr.createnumismatics.content.bank.CardItem;
import dev.ithundxr.createnumismatics.content.backend.Coin;
import dev.ithundxr.createnumismatics.content.bank.IDCardItem;
import dev.ithundxr.createnumismatics.content.bank.blaze_banker.BankingGuideItem;
import dev.ithundxr.createnumismatics.content.coins.CoinItem;
import dev.ithundxr.createnumismatics.util.TextUtils;
import net.minecraft.core.registries.Registries;

import java.util.EnumMap;

public class NumismaticsItems {
	private static final CreateRegistrate REGISTRATE = Numismatics.registrate();

	private static ItemEntry<CoinItem> makeCoin(Coin coin) {
		return REGISTRATE.item(coin.getName(), CoinItem.create(coin))
			.tag(NumismaticsTags.AllItemTags.COINS.tag)
			.lang(coin.getDisplayName())
			.properties(p -> p.rarity(coin.rarity))
			.model((c, p) -> p.generated(c, p.modLoc("item/coin/" + coin.getName())))
			.register();
	}

	public static final EnumMap<Coin, ItemEntry<CoinItem>> COINS = new EnumMap<>(Coin.class);

	static {
		for (Coin coin : Coin.values()) {
			COINS.put(coin, makeCoin(coin));
		}
	}

	public static ItemEntry<CoinItem> getCoin(Coin coin) {
		return COINS.get(coin);
	}

	public static final DyedItemList<CardItem> CARDS = new DyedItemList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.item(colorName+"_card", p -> new CardItem(p, color))
			.properties(p -> p.stacksTo(1))
			.tag(NumismaticsTags.AllItemTags.CARDS.tag)
			.lang(TextUtils.titleCaseConversion(color.getName()) + " Card")
			.model((c, p) -> p.generated(c, Numismatics.asResource("item/card/"+colorName+"_card")))
			.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "item.numismatics.bank_card"))
			.register();
	});

	public static final DyedItemList<IDCardItem> ID_CARDS = new DyedItemList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.item(colorName+"_id_card", p -> new IDCardItem(p, color))
			.properties(p -> p.stacksTo(16))
			.tag(NumismaticsTags.AllItemTags.ID_CARDS.tag)
			.lang(TextUtils.titleCaseConversion(color.getName()) + " ID Card")
			.model((c, p) -> p.generated(c, Numismatics.asResource("item/id_card/"+colorName+"_id_card")))
			.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "item.numismatics.id_card"))
			.register();
	});

	public static final ItemEntry<BankingGuideItem> BANKING_GUIDE = REGISTRATE.item("banking_guide", BankingGuideItem::new)
		.lang("Banking Guide")
		.register();

	public static void register() {
		// load the class and register everything
		Numismatics.LOGGER.info("Registering items for " + Numismatics.NAME);
	}
}
