package com.example.kitchenpal;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFavouritesFragment} factory method to
 * create an instance of this fragment.
 */
public class ProfileMyRecipesFragment extends Fragment {
    //
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//
    public ProfileMyRecipesFragment() {
        // Required empty public constructor
    }
    //
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment profileFavourites.
//     */
//    public static profileFavourites newInstance(String param1, String param2) {
//        profileFavourites fragment = new profileFavourites();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//    RecyclerView profileVerticalRec;
//    List<ProfileFavModel> profileVerNodeList;
//    ProfileFavAdapter profileFavAdapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View root = inflater.inflate(R.layout.fragment_profile_my_recipes, container, false);
//
//        profileVerticalRec = root.findViewById(R.id.profile_myRecipes_rec);
//        profileVerNodeList = new ArrayList<>();
//        profileVerNodeList.add(new ProfileFavModel(R.drawable.pizza, "mine 1"));
//        profileVerNodeList.add(new ProfileFavModel(R.drawable.pizza, "mine 2"));
//        profileVerNodeList.add(new ProfileFavModel(R.drawable.pizza, "mine 3"));
//        profileVerNodeList.add(new ProfileFavModel(R.drawable.burger, "mine 4"));
//        profileVerNodeList.add(new ProfileFavModel(R.drawable.fries, "mine 5"));
//        profileVerNodeList.add(new ProfileFavModel(R.drawable.fries, "mine 6"));
//        profileVerNodeList.add(new ProfileFavModel(R.drawable.burger, "mine 7"));
//        profileVerNodeList.add(new ProfileFavModel(R.drawable.fries, "END"));
//
//        profileFavAdapter = new ProfileFavAdapter(getActivity(), profileVerNodeList);
//        profileVerticalRec.setAdapter(profileFavAdapter);
//        profileVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//        profileVerticalRec.setHasFixedSize(true);
//        profileVerticalRec.setNestedScrollingEnabled(false);
//        // Inflate the layout for this fragment
//        return root;
//    }
}