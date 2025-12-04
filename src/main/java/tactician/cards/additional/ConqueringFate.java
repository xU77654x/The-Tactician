package tactician.cards.additional;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.Tactician9CopyCard;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.effects.cards.ConqueringFateEffect;
import tactician.effects.cards.LungeEffect;
import tactician.powers.weapons.*;
import tactician.util.CardStats;
import tactician.util.CustomTags;
import tactician.util.Wiz;
import java.util.ArrayList;

public class ConqueringFate extends Tactician9CopyCard {
	public static final String ID = makeID(ConqueringFate.class.getSimpleName());
	private static final CardStats info = new CardStats(
			TacticianRobin.Meta.CARD_COLOR,
			CardType.ATTACK,
			CardRarity.UNCOMMON,
			CardTarget.ENEMY,
			3
	);

	public ConqueringFate() {
		super(ID, info);
		setDamage(21, 6);
		setMagic(1, 0);
		tags.add(CustomTags.COPY);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		Color color = Color.LIGHT_GRAY.cpy();
		if (AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { color = Color.RED.cpy(); }
		else if (AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { color = Color.BLUE.cpy(); }
		else if (AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { color = Color.GREEN.cpy(); }
		else if (AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { color = Color.PINK.cpy(); }
		else if (AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { color = Color.CYAN.cpy(); }
		else if (AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { color = Color.ORANGE.cpy(); }
		else if (AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { color = Color.YELLOW.cpy(); }
		else if (AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { color = Color.PURPLE.cpy(); }

		addToBot(new VFXAction(new ConqueringFateEffect(m.hb.cX, m.hb.y, "tactician:ConqueringFate", 2.67F, color)));
		addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

		if (AbstractDungeon.player instanceof TacticianRobin) {
			ArrayList<AbstractCard> easyCardList = new ArrayList<>();
			easyCardList.add(new Weapon1Sword(() -> { if (!p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); } }));
			easyCardList.add(new Weapon2Lance(() -> { if (!p.hasPower(Weapon2LancePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p))); } }));
			easyCardList.add(new Weapon3Axe(() -> { if (!p.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); } }));
			easyCardList.add(new Weapon4Bow(() -> { if (!p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); } }));
			easyCardList.add(new Weapon5Wind(() -> { if (!p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); } }));
			easyCardList.add(new Weapon6Fire(() -> { if (!p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); } }));
			easyCardList.add(new Weapon7Thunder(() -> { if (!p.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); } }));
			easyCardList.add(new Weapon8Dark(() -> { if (!p.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); } }));
			addToBot(new EasyModalChoiceAction(easyCardList));
		}
		addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, this.magicNumber)));
	}

	@Override
	public void calculateCardDamage(AbstractMonster m) {
		if (m != null) {
			int realDamage = baseDamage;
			baseDamage += Wiz.playerWeaponCalc(m, 9);
			super.calculateCardDamage(m);
			baseDamage = realDamage;
			this.isDamageModified = (damage != baseDamage);
		}
	}

	@Override
	public AbstractCard makeCopy() { return new ConqueringFate(); }
}