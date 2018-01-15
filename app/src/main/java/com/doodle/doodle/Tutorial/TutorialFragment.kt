package com.doodle.doodle.Tutorial

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doodle.doodle.Login.LoginActivity

import com.doodle.doodle.R
import com.doodle.doodle.Splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_tutorial.view.*


class TutorialFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var flag: Int? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
            flag = arguments.getInt("flag")
            Log.d("fat", flag.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_tutorial, container, false)
        Log.d("fat", flag.toString())
        when (flag) {
            0 -> {
                Log.d("fat", flag.toString())
                v.tuto_image.setImageResource(R.drawable.tuto1)
            }
            1 -> {
                Log.d("fat", flag.toString())
                v.tuto_image.setImageResource(R.drawable.tuto2)
            }
            2 -> {
                Log.d("fat", flag.toString())
                v.tuto_image.setImageResource(R.drawable.tuto3)
            }
            3 -> {
                Log.d("fat", flag.toString())
                v.tuto_image.setImageResource(R.drawable.tuto4)
            }
            4 -> {
                Log.d("fat", flag.toString())
                v.tuto_image.setImageResource(R.drawable.tuto5)
            }
            5->{
                startActivity(Intent(activity,SelectActivity::class.java))
            }

        }

        return v
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TutorialFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): TutorialFragment {
            val fragment = TutorialFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
