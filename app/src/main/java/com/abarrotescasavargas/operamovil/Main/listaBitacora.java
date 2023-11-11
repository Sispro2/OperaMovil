package com.abarrotescasavargas.operamovil.Main;

public class listaBitacora {

//    ImageButton btnAnadir;
//    //Button btnActualizar;
//
//    int contador=0;
//
//    ListView listaBitacora;
//
//    List<String>mLista= new ArrayList<>();
//    ArrayAdapter<String> mAdapter;
//
//    String estatus="0";
//
//    String NomPro1="";
//    String sqlFecha="";
//    String sqlHoraEntre="";
//    String sqlclavePro="";
//    String sqlnumFactura="";
//    String sqlplacas="";
//    String nomTransportista="";
//    String sqlnomRecibido="";
//    String observaciones="";
//    String sqlRFCPRO="";
//    String sqlFOLFIS="";
//    String sqlIMPORT="";
//
//    Cursor fila;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lista_bitacora);
//
//        btnAnadir = findViewById(R.id.btnAnadir);
//        listaBitacora =findViewById(R.id.listaConteoTransfer);
//        listaBitacora.setOnItemClickListener(this);
//
//        //btnActualizar=findViewById(R.id.btnActualizar);
//
//
//        mLista.clear();
//
//        try {
//
//            AdminSQLite admin = new AdminSQLite(getApplicationContext(), "prueba", null, 1);
//
//            SQLiteDatabase db = admin.getReadableDatabase();
//            fila = db.rawQuery("select * from prueba ", null);
//
//            int a=0;
//
//            try {
//                if(fila.getCount()==0){
//                    Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
//                }else{
//                    fila.moveToFirst();
//                    do{
//                        //String nom = fila.getString(fila.getColumnIndex("proveedor"));
//                        // Toast.makeText(getApplicationContext(), "Existe    " + nom + a, Toast.LENGTH_SHORT).show();
//                        //a++;
//                        mLista.add(fila.getString(fila.getColumnIndex("proveedor")));
//
//                    }while(fila.moveToNext());
//                }
//
//                mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mLista);
//                listaBitacora.setAdapter(mAdapter);
//
//
//            }catch (Exception ex){
//                Toast.makeText(getApplicationContext(), "error "+ex.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//        }catch (Exception ex){
//            Toast.makeText(getApplicationContext(), ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//
//
//       /* btnActualizar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                actualizaTabla();
//
//            }
//        });
//
//        */
//
//
//        btnAnadir.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                /*AdminSQLite admin = new AdminSQLite(getApplicationContext(),"prueba",null,1);
//                SQLiteDatabase bd = admin.getWritableDatabase();
//
//                bd.execSQL("DROP TABLE IF EXISTS prueba");
//                 */
//                vista();
//            }
//        });
//    }
//
//
//
//    public void vista(){
//
//
//        //Intent intent = new Intent (this, Registro2.class);
//        Intent intent = new Intent (this, RegistroBitacora.class);
//       // Intent intent = new Intent (this, CargaFoto.class);
//
//        intent.putExtra("estatus",estatus.toString());
//        /*
//        * Luego debo de cambiar estas lineas, solo las puse para evitar el nullpointer
//        intent.putExtra("estatus","");
//        intent.putExtra("nombrePro","");
//        intent.putExtra("fecha","");
//        intent.putExtra("HoraEntre","");
//        intent.putExtra("clavePro","");
//        intent.putExtra("numFactura","");
//        intent.putExtra("placas","");
//        intent.putExtra("nomTransportista","");
//        intent.putExtra("nomRecibido","");
//        intent.putExtra("observaciones","");
//        intent.putExtra("sqlRFCPRO","");
//        intent.putExtra("sqlFOLFIS","");
//        intent.putExtra("sqlIMPORT","");
//        */
//
//
//
//        startActivity(intent);
//        overridePendingTransition(R.transition.in_left, R.transition.out_left);
//    }
//
//
//   /* public void actualizaTabla(){
//
//        mLista.clear();
//
//        try {
//
//            AdminSQLite admin = new AdminSQLite(getApplicationContext(), "prueba", null, 1);
//
//            SQLiteDatabase db = admin.getReadableDatabase();
//            fila = db.rawQuery("select * from prueba ", null);
//
//            int a=0;
//
//            try {
//                if(fila.getCount()==0){
//                    Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
//                }else{
//                    fila.moveToFirst();
//                    do{
//                        //String nom = fila.getString(fila.getColumnIndex("proveedor"));
//                       // Toast.makeText(getApplicationContext(), "Existe    " + nom + a, Toast.LENGTH_SHORT).show();
//                        //a++;
//                        mLista.add(fila.getString(fila.getColumnIndex("proveedor")));
//
//                    }while(fila.moveToNext());
//                }
//
//                mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mLista);
//                listaBitacora.setAdapter(mAdapter);
//
//
//            }catch (Exception ex){
//                Toast.makeText(getApplicationContext(), "error "+ex.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//        }catch (Exception ex){
//            Toast.makeText(getApplicationContext(), ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    */
//
//
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        String nombrePro = (String) listaBitacora.getItemAtPosition(position);
//
//        AdminSQLite admin = new AdminSQLite(getApplicationContext(), "prueba", null, 1);
//
//        SQLiteDatabase db = admin.getReadableDatabase();
//        fila = db.rawQuery("select * from prueba  where proveedor='"+ nombrePro +"'", null);
//
//        try {
//            if(fila.getCount()==0){
//                Toast.makeText(getApplicationContext(), "No hay registros", Toast.LENGTH_LONG).show();
//            }else{
//                fila.moveToFirst();
//                do{
//
//                    NomPro1 = fila.getString(fila.getColumnIndex("proveedor"));
//                    sqlFecha = fila.getString(fila.getColumnIndex("fecha"));
//                    sqlHoraEntre= fila.getString(fila.getColumnIndex("HoraEntre"));
//                    sqlclavePro = fila.getString(fila.getColumnIndex("clavePro"));
//                    sqlnumFactura = fila.getString(fila.getColumnIndex("numFactura"));
//                    sqlplacas = fila.getString(fila.getColumnIndex("placas"));
//                    nomTransportista = fila.getString(fila.getColumnIndex("nomTransportista"));
//                    sqlnomRecibido = fila.getString(fila.getColumnIndex("nomRecibido"));
//                    observaciones = fila.getString(fila.getColumnIndex("observaciones"));
//
//                    sqlRFCPRO = fila.getString(fila.getColumnIndex("sqlRFCPRO"));
//                    sqlFOLFIS = fila.getString(fila.getColumnIndex("sqlFOLFIS"));
//                    sqlIMPORT = fila.getString(fila.getColumnIndex("sqlIMPORT"));
//
//
//
//                }while(fila.moveToNext());
//            }
//
//
//        }catch (Exception ex){
//            Toast.makeText(getApplicationContext(), "error "+ex.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        Intent intent = new Intent (this, Registro2.class);
//
//        intent.putExtra("nombrePro",NomPro1.toString());
//        intent.putExtra("fecha",sqlFecha.toString());
//        intent.putExtra("HoraEntre",sqlHoraEntre.toString());
//        intent.putExtra("clavePro",sqlclavePro.toString());
//        intent.putExtra("numFactura",sqlnumFactura.toString());
//        intent.putExtra("placas",sqlplacas.toString());
//        intent.putExtra("nomTransportista",nomTransportista.toString());
//        intent.putExtra("nomRecibido",sqlnomRecibido.toString());
//        intent.putExtra("observaciones",observaciones.toString());
//
//        intent.putExtra("sqlRFCPRO",sqlRFCPRO.toString());
//        intent.putExtra("sqlFOLFIS",sqlFOLFIS.toString());
//        intent.putExtra("sqlIMPORT",sqlIMPORT.toString());
//
//        startActivity(intent);
//        overridePendingTransition(R.transition.in_left, R.transition.out_left);
//
//    }
}