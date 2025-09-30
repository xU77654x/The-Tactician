package tactician.effects.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import tactician.actions.PlaySoundAction;

public class Tactician3AxeEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final String soundKey;
	private final float volume;

	public Tactician3AxeEffect(float x, float y, String soundKey, float volume) {
		this.x = x;
		this.y = y;
		this.soundKey = soundKey;
		this.volume = volume;
		this.duration = 0.075F;
	}

	@Override
	public void update() {
		AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
		// AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.x, this.y, ));
		this.isDone = true;
	}

	@Override
	public void render(SpriteBatch sb) {}

	@Override
	public void dispose() {}
}