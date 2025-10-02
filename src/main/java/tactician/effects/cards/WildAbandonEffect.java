package tactician.effects.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import tactician.actions.PlaySoundAction;

public class WildAbandonEffect extends AbstractGameEffect {
	private final float x;
	private final float y;
	private final String soundKey;
	private final float volume;
	private boolean fired;

	public WildAbandonEffect(float x, float y, String soundKey, float volume) {
		this.x = x;
		this.y = y;
		this.color = Color.FOREST.cpy();
		this.soundKey = soundKey;
		this.volume = volume;
	}

	@Override
	public void update() {
		AbstractDungeon.actionManager.addToTop(new PlaySoundAction(this.soundKey, volume));
		AbstractDungeon.actionManager.addToTop(new VFXAction(new ViolentAttackEffect(this.x, this.y, this.color)));
		for (int i = 0; i < 4; i++) { AbstractDungeon.actionManager.addToTop(new VFXAction(new StarBounceEffect(this.x, this.y))); }
		this.isDone = true;
	}

	@Override
	public void render(SpriteBatch sb) { }

	@Override
	public void dispose() {}
}