//核心资源显示
//原作者miner
var table = new Table();
var usedItems = new ObjectSet();
var scale = 1;
function build(){
	table.clear();
	table.background(Styles.black3);	
	var items = table.table().get();
	var rebuild = run(() => {
		items.clear();
		let i = 0;
		usedItems.each((item) => {
			items.image(item.uiIcon).size(Vars.iconSmall * scale);
			items.label(() => "" + UI.formatAmount(Vars.player.core() == null ? 0 : Vars.player.core().items.get(item))).padRight(5).minWidth(Vars.iconSmall * scale + 1).get().setFontScale(scale);
			if(++i % 5 == 0) items.row();});});
				items.update(() => {
		Vars.content.items().each(item => {
			if(Vars.player.core() != null && Vars.player.core().items.get(item) > 0 ) usedItems.add(item);});
		rebuild.run();});
	table.row();}//显示栏
Events.on(ResetEvent, e => {
	usedItems.clear();});
Events.on(EventType.ClientLoadEvent, cons(e => {
Vars.ui.settings.game.checkPref("资源显示", true);
	build();
	Vars.ui.hudGroup.fill(cons(t => {
		t.left().name = "coreItems/info";
		t.visibility = boolp(()=> Core.settings.getBool("资源显示"));
		t.add(table);	
		t.addListener(extend(InputListener, {
			lastx: 0,
			lasty: 0,	
			touchDown(event, x, y, pointer, button){
				var v = t.localToParentCoordinates(Tmp.v1.set(x, y));
				this.lastx = v.x;
				this.lasty = v.y;
				return true;},	
			touchDragged(event, x, y, pointer){
				var v = t.localToParentCoordinates(Tmp.v1.set(x, y));
				t.translation.add(v.x - this.lastx, v.y - this.lasty);
				this.lastx = v.x;
				this.lasty = v.y;	},	}));	}));}));
function countUnit(unitType, team){
	return team.data().countType(unitType);}
function countPlayer(team){
	return Groups.player.count(player => player.team() == team);}
function countMiner(team){
	return team.data().units.count(u => u.controller instanceof MinerAI);}