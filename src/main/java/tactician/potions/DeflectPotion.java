package tactician.potions;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.*;
import java.util.ArrayList;
import static tactician.TacticianMod.makeID;

public class DeflectPotion extends BasePotion {
	public static final String ID = makeID(DeflectPotion.class.getSimpleName());
	private static final Color LIQUID_COLOR = Color.BLUE;
	private static final Color HYBRID_COLOR = Color.CORAL;
	private static final Color SPOTS_COLOR = null; // CardHelper.getColor(255, 0, 255);

	public DeflectPotion() {
		super(ID, 16, PotionRarity.COMMON, PotionSize.SPIKY, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
		playerClass = TacticianRobin.Meta.TACTICIAN;
		this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordProper("tactician:deflect")), GameDictionary.keywords.get("tactician:deflect")));
	}

	@Override
	public String getDescription() { return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1]; }

	@Override
	public void use(AbstractCreature target) {
		if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			if (AbstractDungeon.player instanceof TacticianRobin) {
				ArrayList<AbstractCard> easyCardList = new ArrayList<>();
				easyCardList.add(new Weapon1Sword(() -> { if (!AbstractDungeon.player.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon1SwordPower(AbstractDungeon.player))); } }));
				easyCardList.add(new Weapon2Lance(() -> { if (!AbstractDungeon.player.hasPower(Weapon2LancePower.POWER_ID)) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon2LancePower(AbstractDungeon.player))); } }));
				easyCardList.add(new Weapon3Axe(() -> { if (!AbstractDungeon.player.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon3AxePower(AbstractDungeon.player))); } }));
				easyCardList.add(new Weapon4Bow(() -> { if (!AbstractDungeon.player.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon4BowPower(AbstractDungeon.player))); } }));
				easyCardList.add(new Weapon5Wind(() -> { if (!AbstractDungeon.player.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon5WindPower(AbstractDungeon.player))); } }));
				easyCardList.add(new Weapon6Fire(() -> { if (!AbstractDungeon.player.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon6FirePower(AbstractDungeon.player))); } }));
				easyCardList.add(new Weapon7Thunder(() -> { if (!AbstractDungeon.player.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon7ThunderPower(AbstractDungeon.player))); } }));
				easyCardList.add(new Weapon8Dark(() -> { if (!AbstractDungeon.player.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Weapon8DarkPower(AbstractDungeon.player))); } }));
				addToBot(new PlaySoundAction("tactician:WeaponSelect", 1.50F));
				addToBot(new EasyModalChoiceAction(easyCardList));
			}
			addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DeflectPower(potency), potency));
		}
	}

	@Override
	public BasePotion makeCopy() { return new DeflectPotion(); }
}