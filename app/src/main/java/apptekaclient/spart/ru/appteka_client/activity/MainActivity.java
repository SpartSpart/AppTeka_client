package apptekaclient.spart.ru.appteka_client.activity;

import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import apptekaclient.spart.ru.appteka_client.listview.ListViewAdapter;
import apptekaclient.spart.ru.appteka_client.listview.model.DrugListViewModel;

public class MainActivity extends ListActivity {
    // private ListView mainView;
    private ArrayList<DrugListViewModel> listViewModels;
    ListViewAdapter adapter;
    private String authorization;
    public static Set<Integer> changedID = new HashSet<>(); //For saving id of objects that were changed in the listView for further changing in DB

    ///////////
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
//    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//            "Linux", "OS/2" };
//    // Использование собственного шаблона
//    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//            R.layout.drug_list_view_model, R.id.label, values);
//    setListAdapter(adapter);
//}

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        String item = (String) getListAdapter().getItem(position);
//        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
//    }
///////////


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        listViewModels = new ArrayList<>();
//        //changedID.clear();
//        setContentView(R.layout.activity_main_list);
//
//     //   Toolbar toolbar = findViewById(R.id.mainToolbar);
//
//       // setSupportActionBar(toolbar);
//
//   //     mainView = findViewById(R.id.drugList);
//
//        authorization = getIntent().getStringExtra("Authorization");
//
//       // ArrayList<DrugModel> drugModels = new ArrayList<>(getAllDrugs(authorization));
//
//        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//                "Linux", "OS/2" };
//        // Использование собственного шаблона
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                R.layout.drug_list_view_model, R.id.label, values);
//        setListAdapter(adapter);
//
//


        //   buildListModel(drugModels);

        // }
//
//    public void buildListModel(ArrayList<DrugModel> drugModels) {
//        Long id;
//        String name;
//        String type;
//        String count;
//        String appointment;
//        Date date;
//
//        if (!drugModels.isEmpty()) {
//            for (DrugModel drug : drugModels) {
//                id = drug.getId();
//                name = drug.getName();
//                type = drug.getType();
//                count = drug.getCount();
//                appointment = drug.getAppointment();
//                date = drug.getDate();
//
//                DrugListViewModel model = new DrugListViewModel(id, name);
//                listViewModels.add(model);
//            }
//
//        }
//
//        adapter = new ListViewAdapter(getApplicationContext(), 0, listViewModels);
//        mainView.setAdapter(adapter);
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_drug_list, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        switch (id) {
//            case R.id.action_save: {
//                try {
//        //            updateSecrets();
//                } catch (Exception e) {
//                    Toast.makeText(this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();
//                }
//                return true;
//            }
//            case R.id.action_new: {
//  //              setSecretDialog("New Secret", "Enter Data", "", "", "");
//
//                return true;
//            }
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


//    public Collection<DrugModel> getAllDrugs(String authorization) {
//        GetAllDrugs getAllDrugs = new GetAllDrugs(authorization);
//
//        try {
//            return getAllDrugs.execute().get();
//
//        } catch (InterruptedException e) {
//            Toast.makeText(this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();
//        } catch (ExecutionException e) {
//            Toast.makeText(this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();
//        }
//
//        return null;
//
//    }

//    void setSecretDialog(String title,
//                         String message,
//                         final String description,
//                         final String login,
//                         final String password) {
//
//
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//
//        dialog.setTitle(title);
//        dialog.setMessage(message);
//
//// Set an EditText view to get user input
//        final EditText descriptionText = new EditText(this);
//        final EditText loginText = new EditText(this);
//        final EditText passwordText = new EditText(this);
//
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//
//        descriptionText.setText(description);
//        loginText.setText(login);
//        passwordText.setText(password);
//
//        descriptionText.setHint("Description");
//        loginText.setHint("Login");
//        passwordText.setHint("Password");
//
//        layout.addView(descriptionText);
//        layout.addView(loginText);
//        layout.addView(passwordText);
//
//        dialog.setView(layout);
//
//        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                String description = descriptionText.getText().toString();
//                String login = loginText.getText().toString();
//                String password = passwordText.getText().toString();
//
//                if (description.equals("") && login.equals("") && password.equals(""))
//                    return;
//
//                AddDrug addSecret = null;
//                try {
//                    addSecret = new AddDrug(authorization,
//                            Crypto.encryptString(description),
//                            Crypto.encryptString(login),
//                            Crypto.encryptString(password));
//                } catch (Exception e) {
//                    Toast.makeText(getBaseContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
//                }
//
//
//                try {
//                    Long id = addSecret.execute().get();
//                    if (id != null) {
//                        listViewModels.add(new DrugListViewModel(id,
//                                descriptionText.getText().toString(),
//                                loginText.getText().toString(),
//                                passwordText.getText().toString()));
//                    } else
//                        Toast.makeText(getBaseContext(), "Error: Something was wrong", Toast.LENGTH_LONG).show();
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                // Canceled.
//            }
//        });
//
//        dialog.show();
//    }

//    private List<DrugModel> secretsToUpdate() throws Exception {
//        ArrayList<DrugModel> drugModels = new ArrayList<>();
////        HashSet <String> set= new HashSet <String>();
//        Iterator iterator = changedID.iterator();
//        while (iterator.hasNext()) {
//            int i = (int) iterator.next();
//            Long id = listViewModels.get(i).getId();
//            String description = listViewModels.get(i).getDescription();
//            String login = listViewModels.get(i).getLogin();
//            String password = listViewModels.get(i).getPassword();
//            if (description.equals("") && login.equals("") && password.equals(""))
//                drugModels.add(new DrugModel(id, description, login, password));
//            else
//                drugModels.add(new DrugModel(id,
//                        Crypto.encryptString(description),
//                        Crypto.encryptString(login),
//                        Crypto.encryptString(password)));
//        }
//        changedID.clear();
//        return drugModels;
//    }

//    private void updateSecrets() throws Exception {
//        List<DrugModel> drugModels = secretsToUpdate();
//        boolean bool = false;
//        if (!drugModels.isEmpty()) {
//            UpdateSecrets updateSecrets = new UpdateSecrets(authorization, drugModels);
//            try {
//                bool = updateSecrets.execute().get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        if (bool) {
//            adapter.deleteVoidRows();
//            Toast.makeText(getBaseContext(), "Updated", Toast.LENGTH_LONG).show();
//        } else
//            Toast.makeText(getBaseContext(), "No secrets to Update", Toast.LENGTH_LONG).show();
//    }


    }
}
