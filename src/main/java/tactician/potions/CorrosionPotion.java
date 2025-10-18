package tactician.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import tactician.character.TacticianRobin;
import static com.badlogic.gdx.graphics.Color.PURPLE;
import static com.badlogic.gdx.graphics.Color.WHITE;
import static tactician.TacticianMod.makeID;

public class CorrosionPotion extends BasePotion {
	public static final String ID = makeID(CorrosionPotion.class.getSimpleName());
	private static final Color LIQUID_COLOR = new Color(Color.PURPLE);
	private static final Color HYBRID_COLOR = new Color(Color.WHITE);
	private static final Color SPOTS_COLOR = null; // CardHelper.getColor(255, 0, 255);

	public CorrosionPotion() {
		super(ID, 0, PotionRarity.UNCOMMON, PotionSize.T, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
		playerClass = TacticianRobin.Meta.TACTICIAN;
	}

	@Override
	public String getDescription() { return DESCRIPTIONS[0]; }

	@Override
	public void use(AbstractCreature target) { if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			addToBot(new ExhaustAction(false, true, true));
		}
	}

	@Override
	public void addAdditionalTips() {
		this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.EXHAUST.NAMES[0]), GameDictionary.keywords.get(GameDictionary.EXHAUST.NAMES[0])));
	}

	@Override
	public BasePotion makeCopy() { return new CorrosionPotion(); }
}