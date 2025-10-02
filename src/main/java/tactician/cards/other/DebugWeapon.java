package tactician.cards.other;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.*;
import tactician.util.CardStats;

import java.util.ArrayList;

@AutoAdd.Ignore
public class DebugWeapon extends TacticianCard {
	public static final String ID = makeID(DebugWeapon.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.SKILL,
			CardRarity.SPECIAL,
			CardTarget.ENEMY,
			0
	);
	public DebugWeapon() {
		super(ID, info);
		setMagic(0, 2);
		setSelfRetain(true);
		this.purgeOnUse = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.upgraded) { addToBot(new GainEnergyAction(this.magicNumber)); }
		ArrayList<AbstractCard> easyCardList = new ArrayList<>();
		easyCardList.add(new Weapon1Sword(() -> addToBot(new ApplyPowerAction(m, p, new Weapon1SwordPower(m)))));
		easyCardList.add(new Weapon2Lance(() -> addToBot(new ApplyPowerAction(m, p, new Weapon2LancePower(m)))));
		easyCardList.add(new Weapon3Axe(() -> addToBot(new ApplyPowerAction(m, p, new Weapon3AxePower(m)))));
		easyCardList.add(new Weapon4Bow(() -> addToBot(new ApplyPowerAction(m, p, new Weapon4BowPower(m)))));
		easyCardList.add(new Weapon5Wind(() -> addToBot(new ApplyPowerAction(m, p, new Weapon5WindPower(m)))));
		easyCardList.add(new Weapon6Fire(() -> addToBot(new ApplyPowerAction(m, p, new Weapon6FirePower(m)))));
		easyCardList.add(new Weapon7Thunder(() -> addToBot(new ApplyPowerAction(m, p, new Weapon7ThunderPower(m)))));
		easyCardList.add(new Weapon8Dark(() -> addToBot(new ApplyPowerAction(m, p, new Weapon8DarkPower(m)))));
		addToBot(new PlaySoundAction("tactician:WeaponSelect", 1.50F));
		addToBot(new EasyModalChoiceAction(easyCardList));
	}

	@Override
	public AbstractCard makeCopy() { return new DebugWeapon(); }
}