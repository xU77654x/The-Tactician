package tactician.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tactician.character.TacticianRobin;
import tactician.powers.ShovePower;
import static tactician.TacticianMod.makeID;

public class Concoction extends TacticianPotion {
	public static final String ID = makeID(Concoction.class.getSimpleName());
	private static final Color LIQUID_COLOR = new Color(Color.BROWN);
	private static final Color HYBRID_COLOR = new Color(Color.WHITE);
	private static final Color SPOTS_COLOR = null; // CardHelper.getColor(255, 0, 255);

	public Concoction() {
		super(ID, 6, PotionRarity.RARE, PotionSize.T, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
		playerClass = TacticianRobin.Meta.TACTICIAN;
		this.labOutlineColor = new Color(Color.PURPLE);
	}

	@Override
	public String getDescription() { return DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + (this.potency / 3) + DESCRIPTIONS[2]; }

	@Override
	public void use(AbstractCreature target) {
		if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.potency));
			addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ShovePower((potency / 3))));
		}
		else { AbstractDungeon.player.heal(this.potency); }
	}

	public boolean canUse() {
		if (AbstractDungeon.actionManager.turnHasEnded && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) { return false; }
		return (AbstractDungeon.getCurrRoom()).event == null || !((AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain);
	}

	@Override
	public TacticianPotion makeCopy() { return new Concoction(); }
}