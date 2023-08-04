package chire.ui;

import arc.Core;
import arc.struct.Seq;
import chire.ui.dialogs.CRJoinDialog;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class CRLegacyIO {

    public static Seq<CRJoinDialog.Server> readServers(){
        Seq<CRJoinDialog.Server> arr = new Seq<>();

        try{
            byte[] bytes = Core.settings.getBytes("server-list");
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(bytes));

            int length = stream.readInt();
            if(length > 0){
                //name of type, irrelevant
                stream.readUTF();

                for(int i = 0; i < length; i++){
                    CRJoinDialog.Server server = new CRJoinDialog.Server();
                    server.ip = stream.readUTF();
                    server.port = stream.readInt();
                    arr.add(server);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return arr;
    }

}
