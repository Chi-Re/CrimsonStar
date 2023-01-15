//信息栏
//进游戏显示 搬运请勿移除版权等声明信息
Events.on(EventType.ClientLoadEvent, cons(e => {
    var dialog = new BaseDialog("炽工业信息栏");
    dialog.cont.image(Core.atlas.find("blazindustry-logo")).row();;
    dialog.buttons.defaults().size(210, 64);
    dialog.buttons.button("@close", run(() => {
        dialog.hide();
    })).size(210, 64);
    dialog.cont.pane((() => {
        var table = new Table();   
         table.add("\n[red]你已添加炽工业[white]\n当前您游玩的模组版本版本是:测试版\n\n\n[red]----——特别感谢——----\n[red]@RA2[white](饱和火力作者)提供的代码模板和贴图,没了他我也不知道怎么做模组\n感谢新科技作者提供的信息栏模板\n[red]----——辅助——----[white]\n手挖沙(作者:神魂)\n核心资源显示 与 缩放强化(作者:miner)\n\n\n下面有更新日志和我的QQ群,出现了什么bug或者对我的模组感兴趣的话可以加群\nB站UID:511023402([red]炽热S[white])").left().growX().wrap().width(600).maxWidth(1000).pad(4).labelAlign(Align.left);
        table.row();
table.button("[blue]加QQ群", run(() => {
    var dialog2 = new BaseDialog("[red]炽工业模组[white]QQ[blue]群二维码");
    var table = new Table();
    dialog2.cont.image(Core.atlas.find("blazindustry-二维码")).row();;
    dialog2.buttons.defaults().size(210, 64);
    dialog2.buttons.button("@close", run(() => {
        dialog2.hide();
    })).size(210, 64);
       dialog2.show();
    })).size(210, 64).row();


    //测试1
    table.button("测试1", run(() => {
    var dialog2 = new BaseDialog("[red]炽工业模组[white]测试1");
    var table = new Table();
    dialog2.cont.image(Core.atlas.find("blazindustry-？")).row();;
    dialog2.buttons.defaults().size(210, 64);
    dialog2.buttons.button("@close", run(() => {
        dialog2.hide();
    })).size(210, 64);
       dialog2.show();
    })).size(210, 64).row();
    //测试1 by:炽热S 没错,就是我!上面这块是我新加的!
    //你只需要更改就可以正常新增按钮,怎么样?是不是非常简单?快去逝一下吧!


table.button("[red]更新日志", run(() => {
    var dialog2 = new BaseDialog("[red]更新日志");
    var table = new Table();
	
	var t = new Table();
	t.add("能不能看懂更新日志都是随缘\n\nX测试版X\n <基础玩法增加\n <科技树新增剧情\n <最基础的矿物和物品增加");
    dialog2.cont.add(new ScrollPane(t)).size(500, 600).row();
    dialog2.buttons.defaults().size(620, 64);
    dialog2.buttons.button("@close", run(() => {
        dialog2.hide();
    })).size(500, 64);
       dialog2.show();
    })).size(210, 64);
        return table;
    })()).grow().center().maxWidth(620);
    dialog.show();
}));
//新科技作者提供